package io.pg.macros

import scala.annotation.StaticAnnotation
import scala.annotation.compileTimeOnly
import scala.reflect.macros.whitebox

object FoldMacros {

  @compileTimeOnly("enable macro paradise to use this")
  class Folds extends StaticAnnotation {
    def macroTransform(annottees: Any*): Any = macro impl.foldsMacroImpl
  }

  object impl {

    def foldsMacroImpl(c: whitebox.Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {
      import c.universe._
      var logs = 0
      def println(text: Any) = c.info(c.enclosingPosition, { logs += 1; logs.toString() } ++ ": " ++ text.toString(), true)

      def uncapitalize(s: String): String = s.headOption match {
        case None                   => ""
        case Some(ch) if ch.isUpper => ch.toLower.toString ++ s.tail
        case Some(_)                => s
      }

      class findSubtypes(parentName: Name) extends Traverser {
        private[this] var knownSubtypes: List[ClassDef] = Nil

        def traverseAll(trees: List[Tree]): List[ClassDef] = {
          knownSubtypes = Nil
          traverseTrees(trees)
          knownSubtypes
        }

        private def isTheTrait(parent: Tree): Boolean = parent match {
          // it would be nice to recognize type aliases and transitive parents too
          case Ident(name) => name == parentName
          case _           => false
        }

        override def traverse(tree: c.universe.Tree): Unit =
          tree match {
            case clazz: ClassDef
                if clazz.mods.hasFlag(Flag.CASE)
                //
                  && clazz.impl.parents.exists(isTheTrait) =>
              knownSubtypes :+= clazz
            case tree =>
              super.traverse(tree)
          }
      }

      case class ClassFold(
        paramTypes: List[Tree],
        caze: CaseDef,
        foldFunctionName: TermName
      )

      def makeClassFold(clazz: ClassDef): ClassFold = {
        val params = clazz.impl.body.collect {
          case v: ValDef if v.mods.hasFlag(Flag.CASEACCESSOR) =>
            v
        }

        val paramTypes = params.map(_.tpt)
        val paramNames = params.map(_.name)

        val classNiceName = uncapitalize(clazz.name.toString)
        val foldFunctionName = TermName(classNiceName)
        val classPatternName = TermName(c.freshName(classNiceName))

        ClassFold(
          paramTypes,
          CaseDef(
            pat = Bind.apply(classPatternName, Typed(Ident(termNames.WILDCARD), Ident(clazz.name))),
            body = q"$foldFunctionName(..${paramNames.map(field => q"$classPatternName.$field")})",
            guard = EmptyTree
          ),
          foldFunctionName
        )
      }

      def makeFoldMethod(clazzez: List[ClassDef]) = {
        def makeFold(folds: List[ClassFold]): Tree = {
          def genCases(returnType: TypeName) = folds.map { cf =>
            q"val ${cf.foldFunctionName}: (..${cf.paramTypes}) => $returnType"
          }

          val typeName = TypeName(c.freshName("A"))

          val matches = Match(q"this", folds.map(_.caze))

          q"""def fold[$typeName](..${genCases(typeName)}): $typeName = $matches"""
        }

        makeFold(clazzez.map(makeClassFold))
      }

      def rebuild(clazz: ClassDef, newStats: List[Tree], companions: List[Tree]): c.Expr[Any] = {
        val newClassImpl = Template(clazz.impl.parents, clazz.impl.self, clazz.impl.body ++ newStats)
        val newClazz = ClassDef.apply(clazz.mods, clazz.name, clazz.tparams, newClassImpl)

        c.Expr(Block(newClazz :: companions, Literal(Constant(()))))
      }

      // actualy matching on the trees we've received

      annottees.map(_.tree).toList match {
        case (sealedTrait: ClassDef) :: others if sealedTrait.tparams.isEmpty && sealedTrait.mods.hasFlag(Flag.TRAIT) =>
          val results = new findSubtypes(sealedTrait.name).traverseAll(others)

          val foldMethod = makeFoldMethod(results)
          rebuild(sealedTrait, List(foldMethod), others)

        case (clazz: ClassDef) :: others if clazz.tparams.isEmpty =>
          val foldMethod = makeFoldMethod(List(clazz))

          rebuild(clazz, List(foldMethod), others)

        case _ =>
          c.abort(c.enclosingPosition, "I *literally* don't know what to do with this.")
      }
    }

  }

}
