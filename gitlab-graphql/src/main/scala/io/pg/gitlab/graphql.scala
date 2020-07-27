package io.pg.gitlab

import caliban.client.CalibanClientError.DecodingError
import caliban.client.FieldBuilder._
import caliban.client.SelectionBuilder._
import caliban.client._
import caliban.client.Operations._
import caliban.client.Value._

object graphql {

  type ID = String

  type ISO8601Date = String

  type JSON = String

  type Time = String

  type UntrustedRegexp = String

  type Upload = String

  sealed trait AccessLevelEnum extends scala.Product with scala.Serializable

  object AccessLevelEnum {
    case object NO_ACCESS extends AccessLevelEnum
    case object GUEST extends AccessLevelEnum
    case object REPORTER extends AccessLevelEnum
    case object DEVELOPER extends AccessLevelEnum
    case object MAINTAINER extends AccessLevelEnum
    case object OWNER extends AccessLevelEnum

    implicit val decoder: ScalarDecoder[AccessLevelEnum] = {
      case StringValue("NO_ACCESS")  => Right(AccessLevelEnum.NO_ACCESS)
      case StringValue("GUEST")      => Right(AccessLevelEnum.GUEST)
      case StringValue("REPORTER")   => Right(AccessLevelEnum.REPORTER)
      case StringValue("DEVELOPER")  => Right(AccessLevelEnum.DEVELOPER)
      case StringValue("MAINTAINER") => Right(AccessLevelEnum.MAINTAINER)
      case StringValue("OWNER")      => Right(AccessLevelEnum.OWNER)
      case other                     => Left(DecodingError(s"Can't build AccessLevelEnum from input $other"))
    }

    implicit val encoder: ArgEncoder[AccessLevelEnum] = new ArgEncoder[AccessLevelEnum] {

      override def encode(value: AccessLevelEnum): Value =
        value match {
          case AccessLevelEnum.NO_ACCESS  => EnumValue("NO_ACCESS")
          case AccessLevelEnum.GUEST      => EnumValue("GUEST")
          case AccessLevelEnum.REPORTER   => EnumValue("REPORTER")
          case AccessLevelEnum.DEVELOPER  => EnumValue("DEVELOPER")
          case AccessLevelEnum.MAINTAINER => EnumValue("MAINTAINER")
          case AccessLevelEnum.OWNER      => EnumValue("OWNER")
        }

      override def typeName: String = "AccessLevelEnum"
    }

  }

  sealed trait AlertManagementAlertSort extends scala.Product with scala.Serializable

  object AlertManagementAlertSort {
    case object updated_desc extends AlertManagementAlertSort
    case object updated_asc extends AlertManagementAlertSort
    case object created_desc extends AlertManagementAlertSort
    case object created_asc extends AlertManagementAlertSort
    case object STARTED_AT_ASC extends AlertManagementAlertSort
    case object STARTED_AT_DESC extends AlertManagementAlertSort
    case object ENDED_AT_ASC extends AlertManagementAlertSort
    case object ENDED_AT_DESC extends AlertManagementAlertSort
    case object CREATED_TIME_ASC extends AlertManagementAlertSort
    case object CREATED_TIME_DESC extends AlertManagementAlertSort
    case object UPDATED_TIME_ASC extends AlertManagementAlertSort
    case object UPDATED_TIME_DESC extends AlertManagementAlertSort
    case object EVENT_COUNT_ASC extends AlertManagementAlertSort
    case object EVENT_COUNT_DESC extends AlertManagementAlertSort
    case object SEVERITY_ASC extends AlertManagementAlertSort
    case object SEVERITY_DESC extends AlertManagementAlertSort
    case object STATUS_ASC extends AlertManagementAlertSort
    case object STATUS_DESC extends AlertManagementAlertSort

    implicit val decoder: ScalarDecoder[AlertManagementAlertSort] = {
      case StringValue("updated_desc")      => Right(AlertManagementAlertSort.updated_desc)
      case StringValue("updated_asc")       => Right(AlertManagementAlertSort.updated_asc)
      case StringValue("created_desc")      => Right(AlertManagementAlertSort.created_desc)
      case StringValue("created_asc")       => Right(AlertManagementAlertSort.created_asc)
      case StringValue("STARTED_AT_ASC")    => Right(AlertManagementAlertSort.STARTED_AT_ASC)
      case StringValue("STARTED_AT_DESC")   => Right(AlertManagementAlertSort.STARTED_AT_DESC)
      case StringValue("ENDED_AT_ASC")      => Right(AlertManagementAlertSort.ENDED_AT_ASC)
      case StringValue("ENDED_AT_DESC")     => Right(AlertManagementAlertSort.ENDED_AT_DESC)
      case StringValue("CREATED_TIME_ASC")  => Right(AlertManagementAlertSort.CREATED_TIME_ASC)
      case StringValue("CREATED_TIME_DESC") => Right(AlertManagementAlertSort.CREATED_TIME_DESC)
      case StringValue("UPDATED_TIME_ASC")  => Right(AlertManagementAlertSort.UPDATED_TIME_ASC)
      case StringValue("UPDATED_TIME_DESC") => Right(AlertManagementAlertSort.UPDATED_TIME_DESC)
      case StringValue("EVENT_COUNT_ASC")   => Right(AlertManagementAlertSort.EVENT_COUNT_ASC)
      case StringValue("EVENT_COUNT_DESC")  => Right(AlertManagementAlertSort.EVENT_COUNT_DESC)
      case StringValue("SEVERITY_ASC")      => Right(AlertManagementAlertSort.SEVERITY_ASC)
      case StringValue("SEVERITY_DESC")     => Right(AlertManagementAlertSort.SEVERITY_DESC)
      case StringValue("STATUS_ASC")        => Right(AlertManagementAlertSort.STATUS_ASC)
      case StringValue("STATUS_DESC")       => Right(AlertManagementAlertSort.STATUS_DESC)
      case other                            => Left(DecodingError(s"Can't build AlertManagementAlertSort from input $other"))
    }

    implicit val encoder: ArgEncoder[AlertManagementAlertSort] = new ArgEncoder[AlertManagementAlertSort] {

      override def encode(value: AlertManagementAlertSort): Value =
        value match {
          case AlertManagementAlertSort.updated_desc      => EnumValue("updated_desc")
          case AlertManagementAlertSort.updated_asc       => EnumValue("updated_asc")
          case AlertManagementAlertSort.created_desc      => EnumValue("created_desc")
          case AlertManagementAlertSort.created_asc       => EnumValue("created_asc")
          case AlertManagementAlertSort.STARTED_AT_ASC    => EnumValue("STARTED_AT_ASC")
          case AlertManagementAlertSort.STARTED_AT_DESC   => EnumValue("STARTED_AT_DESC")
          case AlertManagementAlertSort.ENDED_AT_ASC      => EnumValue("ENDED_AT_ASC")
          case AlertManagementAlertSort.ENDED_AT_DESC     => EnumValue("ENDED_AT_DESC")
          case AlertManagementAlertSort.CREATED_TIME_ASC  => EnumValue("CREATED_TIME_ASC")
          case AlertManagementAlertSort.CREATED_TIME_DESC => EnumValue("CREATED_TIME_DESC")
          case AlertManagementAlertSort.UPDATED_TIME_ASC  => EnumValue("UPDATED_TIME_ASC")
          case AlertManagementAlertSort.UPDATED_TIME_DESC => EnumValue("UPDATED_TIME_DESC")
          case AlertManagementAlertSort.EVENT_COUNT_ASC   => EnumValue("EVENT_COUNT_ASC")
          case AlertManagementAlertSort.EVENT_COUNT_DESC  => EnumValue("EVENT_COUNT_DESC")
          case AlertManagementAlertSort.SEVERITY_ASC      => EnumValue("SEVERITY_ASC")
          case AlertManagementAlertSort.SEVERITY_DESC     => EnumValue("SEVERITY_DESC")
          case AlertManagementAlertSort.STATUS_ASC        => EnumValue("STATUS_ASC")
          case AlertManagementAlertSort.STATUS_DESC       => EnumValue("STATUS_DESC")
        }

      override def typeName: String = "AlertManagementAlertSort"
    }

  }

  sealed trait AlertManagementSeverity extends scala.Product with scala.Serializable

  object AlertManagementSeverity {
    case object CRITICAL extends AlertManagementSeverity
    case object HIGH extends AlertManagementSeverity
    case object MEDIUM extends AlertManagementSeverity
    case object LOW extends AlertManagementSeverity
    case object INFO extends AlertManagementSeverity
    case object UNKNOWN extends AlertManagementSeverity

    implicit val decoder: ScalarDecoder[AlertManagementSeverity] = {
      case StringValue("CRITICAL") => Right(AlertManagementSeverity.CRITICAL)
      case StringValue("HIGH")     => Right(AlertManagementSeverity.HIGH)
      case StringValue("MEDIUM")   => Right(AlertManagementSeverity.MEDIUM)
      case StringValue("LOW")      => Right(AlertManagementSeverity.LOW)
      case StringValue("INFO")     => Right(AlertManagementSeverity.INFO)
      case StringValue("UNKNOWN")  => Right(AlertManagementSeverity.UNKNOWN)
      case other                   => Left(DecodingError(s"Can't build AlertManagementSeverity from input $other"))
    }

    implicit val encoder: ArgEncoder[AlertManagementSeverity] = new ArgEncoder[AlertManagementSeverity] {

      override def encode(value: AlertManagementSeverity): Value =
        value match {
          case AlertManagementSeverity.CRITICAL => EnumValue("CRITICAL")
          case AlertManagementSeverity.HIGH     => EnumValue("HIGH")
          case AlertManagementSeverity.MEDIUM   => EnumValue("MEDIUM")
          case AlertManagementSeverity.LOW      => EnumValue("LOW")
          case AlertManagementSeverity.INFO     => EnumValue("INFO")
          case AlertManagementSeverity.UNKNOWN  => EnumValue("UNKNOWN")
        }

      override def typeName: String = "AlertManagementSeverity"
    }

  }

  sealed trait AlertManagementStatus extends scala.Product with scala.Serializable

  object AlertManagementStatus {
    case object TRIGGERED extends AlertManagementStatus
    case object ACKNOWLEDGED extends AlertManagementStatus
    case object RESOLVED extends AlertManagementStatus
    case object IGNORED extends AlertManagementStatus

    implicit val decoder: ScalarDecoder[AlertManagementStatus] = {
      case StringValue("TRIGGERED")    => Right(AlertManagementStatus.TRIGGERED)
      case StringValue("ACKNOWLEDGED") => Right(AlertManagementStatus.ACKNOWLEDGED)
      case StringValue("RESOLVED")     => Right(AlertManagementStatus.RESOLVED)
      case StringValue("IGNORED")      => Right(AlertManagementStatus.IGNORED)
      case other                       => Left(DecodingError(s"Can't build AlertManagementStatus from input $other"))
    }

    implicit val encoder: ArgEncoder[AlertManagementStatus] = new ArgEncoder[AlertManagementStatus] {

      override def encode(value: AlertManagementStatus): Value =
        value match {
          case AlertManagementStatus.TRIGGERED    => EnumValue("TRIGGERED")
          case AlertManagementStatus.ACKNOWLEDGED => EnumValue("ACKNOWLEDGED")
          case AlertManagementStatus.RESOLVED     => EnumValue("RESOLVED")
          case AlertManagementStatus.IGNORED      => EnumValue("IGNORED")
        }

      override def typeName: String = "AlertManagementStatus"
    }

  }

  sealed trait BlobViewersType extends scala.Product with scala.Serializable

  object BlobViewersType {
    case object rich extends BlobViewersType
    case object simple extends BlobViewersType
    case object auxiliary extends BlobViewersType

    implicit val decoder: ScalarDecoder[BlobViewersType] = {
      case StringValue("rich")      => Right(BlobViewersType.rich)
      case StringValue("simple")    => Right(BlobViewersType.simple)
      case StringValue("auxiliary") => Right(BlobViewersType.auxiliary)
      case other                    => Left(DecodingError(s"Can't build BlobViewersType from input $other"))
    }

    implicit val encoder: ArgEncoder[BlobViewersType] = new ArgEncoder[BlobViewersType] {

      override def encode(value: BlobViewersType): Value =
        value match {
          case BlobViewersType.rich      => EnumValue("rich")
          case BlobViewersType.simple    => EnumValue("simple")
          case BlobViewersType.auxiliary => EnumValue("auxiliary")
        }

      override def typeName: String = "BlobViewersType"
    }

  }

  sealed trait CommitActionMode extends scala.Product with scala.Serializable

  object CommitActionMode {
    case object CREATE extends CommitActionMode
    case object DELETE extends CommitActionMode
    case object MOVE extends CommitActionMode
    case object UPDATE extends CommitActionMode
    case object CHMOD extends CommitActionMode

    implicit val decoder: ScalarDecoder[CommitActionMode] = {
      case StringValue("CREATE") => Right(CommitActionMode.CREATE)
      case StringValue("DELETE") => Right(CommitActionMode.DELETE)
      case StringValue("MOVE")   => Right(CommitActionMode.MOVE)
      case StringValue("UPDATE") => Right(CommitActionMode.UPDATE)
      case StringValue("CHMOD")  => Right(CommitActionMode.CHMOD)
      case other                 => Left(DecodingError(s"Can't build CommitActionMode from input $other"))
    }

    implicit val encoder: ArgEncoder[CommitActionMode] = new ArgEncoder[CommitActionMode] {

      override def encode(value: CommitActionMode): Value =
        value match {
          case CommitActionMode.CREATE => EnumValue("CREATE")
          case CommitActionMode.DELETE => EnumValue("DELETE")
          case CommitActionMode.MOVE   => EnumValue("MOVE")
          case CommitActionMode.UPDATE => EnumValue("UPDATE")
          case CommitActionMode.CHMOD  => EnumValue("CHMOD")
        }

      override def typeName: String = "CommitActionMode"
    }

  }

  sealed trait CommitEncoding extends scala.Product with scala.Serializable

  object CommitEncoding {
    case object TEXT extends CommitEncoding
    case object BASE64 extends CommitEncoding

    implicit val decoder: ScalarDecoder[CommitEncoding] = {
      case StringValue("TEXT")   => Right(CommitEncoding.TEXT)
      case StringValue("BASE64") => Right(CommitEncoding.BASE64)
      case other                 => Left(DecodingError(s"Can't build CommitEncoding from input $other"))
    }

    implicit val encoder: ArgEncoder[CommitEncoding] = new ArgEncoder[CommitEncoding] {

      override def encode(value: CommitEncoding): Value =
        value match {
          case CommitEncoding.TEXT   => EnumValue("TEXT")
          case CommitEncoding.BASE64 => EnumValue("BASE64")
        }

      override def typeName: String = "CommitEncoding"
    }

  }

  sealed trait ContainerExpirationPolicyCadenceEnum extends scala.Product with scala.Serializable

  object ContainerExpirationPolicyCadenceEnum {
    case object EVERY_DAY extends ContainerExpirationPolicyCadenceEnum
    case object EVERY_WEEK extends ContainerExpirationPolicyCadenceEnum
    case object EVERY_TWO_WEEKS extends ContainerExpirationPolicyCadenceEnum
    case object EVERY_MONTH extends ContainerExpirationPolicyCadenceEnum
    case object EVERY_THREE_MONTHS extends ContainerExpirationPolicyCadenceEnum

    implicit val decoder: ScalarDecoder[ContainerExpirationPolicyCadenceEnum] = {
      case StringValue("EVERY_DAY")          => Right(ContainerExpirationPolicyCadenceEnum.EVERY_DAY)
      case StringValue("EVERY_WEEK")         => Right(ContainerExpirationPolicyCadenceEnum.EVERY_WEEK)
      case StringValue("EVERY_TWO_WEEKS")    => Right(ContainerExpirationPolicyCadenceEnum.EVERY_TWO_WEEKS)
      case StringValue("EVERY_MONTH")        => Right(ContainerExpirationPolicyCadenceEnum.EVERY_MONTH)
      case StringValue("EVERY_THREE_MONTHS") => Right(ContainerExpirationPolicyCadenceEnum.EVERY_THREE_MONTHS)
      case other                             => Left(DecodingError(s"Can't build ContainerExpirationPolicyCadenceEnum from input $other"))
    }

    implicit val encoder: ArgEncoder[ContainerExpirationPolicyCadenceEnum] = new ArgEncoder[ContainerExpirationPolicyCadenceEnum] {

      override def encode(value: ContainerExpirationPolicyCadenceEnum): Value =
        value match {
          case ContainerExpirationPolicyCadenceEnum.EVERY_DAY          => EnumValue("EVERY_DAY")
          case ContainerExpirationPolicyCadenceEnum.EVERY_WEEK         => EnumValue("EVERY_WEEK")
          case ContainerExpirationPolicyCadenceEnum.EVERY_TWO_WEEKS    => EnumValue("EVERY_TWO_WEEKS")
          case ContainerExpirationPolicyCadenceEnum.EVERY_MONTH        => EnumValue("EVERY_MONTH")
          case ContainerExpirationPolicyCadenceEnum.EVERY_THREE_MONTHS => EnumValue("EVERY_THREE_MONTHS")
        }

      override def typeName: String = "ContainerExpirationPolicyCadenceEnum"
    }

  }

  sealed trait ContainerExpirationPolicyKeepEnum extends scala.Product with scala.Serializable

  object ContainerExpirationPolicyKeepEnum {
    case object ONE_TAG extends ContainerExpirationPolicyKeepEnum
    case object FIVE_TAGS extends ContainerExpirationPolicyKeepEnum
    case object TEN_TAGS extends ContainerExpirationPolicyKeepEnum
    case object TWENTY_FIVE_TAGS extends ContainerExpirationPolicyKeepEnum
    case object FIFTY_TAGS extends ContainerExpirationPolicyKeepEnum
    case object ONE_HUNDRED_TAGS extends ContainerExpirationPolicyKeepEnum

    implicit val decoder: ScalarDecoder[ContainerExpirationPolicyKeepEnum] = {
      case StringValue("ONE_TAG")          => Right(ContainerExpirationPolicyKeepEnum.ONE_TAG)
      case StringValue("FIVE_TAGS")        => Right(ContainerExpirationPolicyKeepEnum.FIVE_TAGS)
      case StringValue("TEN_TAGS")         => Right(ContainerExpirationPolicyKeepEnum.TEN_TAGS)
      case StringValue("TWENTY_FIVE_TAGS") => Right(ContainerExpirationPolicyKeepEnum.TWENTY_FIVE_TAGS)
      case StringValue("FIFTY_TAGS")       => Right(ContainerExpirationPolicyKeepEnum.FIFTY_TAGS)
      case StringValue("ONE_HUNDRED_TAGS") => Right(ContainerExpirationPolicyKeepEnum.ONE_HUNDRED_TAGS)
      case other                           => Left(DecodingError(s"Can't build ContainerExpirationPolicyKeepEnum from input $other"))
    }

    implicit val encoder: ArgEncoder[ContainerExpirationPolicyKeepEnum] = new ArgEncoder[ContainerExpirationPolicyKeepEnum] {

      override def encode(value: ContainerExpirationPolicyKeepEnum): Value =
        value match {
          case ContainerExpirationPolicyKeepEnum.ONE_TAG          => EnumValue("ONE_TAG")
          case ContainerExpirationPolicyKeepEnum.FIVE_TAGS        => EnumValue("FIVE_TAGS")
          case ContainerExpirationPolicyKeepEnum.TEN_TAGS         => EnumValue("TEN_TAGS")
          case ContainerExpirationPolicyKeepEnum.TWENTY_FIVE_TAGS => EnumValue("TWENTY_FIVE_TAGS")
          case ContainerExpirationPolicyKeepEnum.FIFTY_TAGS       => EnumValue("FIFTY_TAGS")
          case ContainerExpirationPolicyKeepEnum.ONE_HUNDRED_TAGS => EnumValue("ONE_HUNDRED_TAGS")
        }

      override def typeName: String = "ContainerExpirationPolicyKeepEnum"
    }

  }

  sealed trait ContainerExpirationPolicyOlderThanEnum extends scala.Product with scala.Serializable

  object ContainerExpirationPolicyOlderThanEnum {
    case object SEVEN_DAYS extends ContainerExpirationPolicyOlderThanEnum
    case object FOURTEEN_DAYS extends ContainerExpirationPolicyOlderThanEnum
    case object THIRTY_DAYS extends ContainerExpirationPolicyOlderThanEnum
    case object NINETY_DAYS extends ContainerExpirationPolicyOlderThanEnum

    implicit val decoder: ScalarDecoder[ContainerExpirationPolicyOlderThanEnum] = {
      case StringValue("SEVEN_DAYS")    => Right(ContainerExpirationPolicyOlderThanEnum.SEVEN_DAYS)
      case StringValue("FOURTEEN_DAYS") => Right(ContainerExpirationPolicyOlderThanEnum.FOURTEEN_DAYS)
      case StringValue("THIRTY_DAYS")   => Right(ContainerExpirationPolicyOlderThanEnum.THIRTY_DAYS)
      case StringValue("NINETY_DAYS")   => Right(ContainerExpirationPolicyOlderThanEnum.NINETY_DAYS)
      case other                        => Left(DecodingError(s"Can't build ContainerExpirationPolicyOlderThanEnum from input $other"))
    }

    implicit val encoder: ArgEncoder[ContainerExpirationPolicyOlderThanEnum] = new ArgEncoder[ContainerExpirationPolicyOlderThanEnum] {

      override def encode(value: ContainerExpirationPolicyOlderThanEnum): Value =
        value match {
          case ContainerExpirationPolicyOlderThanEnum.SEVEN_DAYS    => EnumValue("SEVEN_DAYS")
          case ContainerExpirationPolicyOlderThanEnum.FOURTEEN_DAYS => EnumValue("FOURTEEN_DAYS")
          case ContainerExpirationPolicyOlderThanEnum.THIRTY_DAYS   => EnumValue("THIRTY_DAYS")
          case ContainerExpirationPolicyOlderThanEnum.NINETY_DAYS   => EnumValue("NINETY_DAYS")
        }

      override def typeName: String = "ContainerExpirationPolicyOlderThanEnum"
    }

  }

  sealed trait DastScanTypeEnum extends scala.Product with scala.Serializable

  object DastScanTypeEnum {
    case object PASSIVE extends DastScanTypeEnum

    implicit val decoder: ScalarDecoder[DastScanTypeEnum] = {
      case StringValue("PASSIVE") => Right(DastScanTypeEnum.PASSIVE)
      case other                  => Left(DecodingError(s"Can't build DastScanTypeEnum from input $other"))
    }

    implicit val encoder: ArgEncoder[DastScanTypeEnum] = new ArgEncoder[DastScanTypeEnum] {

      override def encode(value: DastScanTypeEnum): Value =
        value match {
          case DastScanTypeEnum.PASSIVE => EnumValue("PASSIVE")
        }

      override def typeName: String = "DastScanTypeEnum"
    }

  }

  sealed trait DesignVersionEvent extends scala.Product with scala.Serializable

  object DesignVersionEvent {
    case object NONE extends DesignVersionEvent
    case object CREATION extends DesignVersionEvent
    case object MODIFICATION extends DesignVersionEvent
    case object DELETION extends DesignVersionEvent

    implicit val decoder: ScalarDecoder[DesignVersionEvent] = {
      case StringValue("NONE")         => Right(DesignVersionEvent.NONE)
      case StringValue("CREATION")     => Right(DesignVersionEvent.CREATION)
      case StringValue("MODIFICATION") => Right(DesignVersionEvent.MODIFICATION)
      case StringValue("DELETION")     => Right(DesignVersionEvent.DELETION)
      case other                       => Left(DecodingError(s"Can't build DesignVersionEvent from input $other"))
    }

    implicit val encoder: ArgEncoder[DesignVersionEvent] = new ArgEncoder[DesignVersionEvent] {

      override def encode(value: DesignVersionEvent): Value =
        value match {
          case DesignVersionEvent.NONE         => EnumValue("NONE")
          case DesignVersionEvent.CREATION     => EnumValue("CREATION")
          case DesignVersionEvent.MODIFICATION => EnumValue("MODIFICATION")
          case DesignVersionEvent.DELETION     => EnumValue("DELETION")
        }

      override def typeName: String = "DesignVersionEvent"
    }

  }

  sealed trait DiffPositionType extends scala.Product with scala.Serializable

  object DiffPositionType {
    case object text extends DiffPositionType
    case object image extends DiffPositionType

    implicit val decoder: ScalarDecoder[DiffPositionType] = {
      case StringValue("text")  => Right(DiffPositionType.text)
      case StringValue("image") => Right(DiffPositionType.image)
      case other                => Left(DecodingError(s"Can't build DiffPositionType from input $other"))
    }

    implicit val encoder: ArgEncoder[DiffPositionType] = new ArgEncoder[DiffPositionType] {

      override def encode(value: DiffPositionType): Value =
        value match {
          case DiffPositionType.text  => EnumValue("text")
          case DiffPositionType.image => EnumValue("image")
        }

      override def typeName: String = "DiffPositionType"
    }

  }

  sealed trait EntryType extends scala.Product with scala.Serializable

  object EntryType {
    case object tree extends EntryType
    case object blob extends EntryType
    case object commit extends EntryType

    implicit val decoder: ScalarDecoder[EntryType] = {
      case StringValue("tree")   => Right(EntryType.tree)
      case StringValue("blob")   => Right(EntryType.blob)
      case StringValue("commit") => Right(EntryType.commit)
      case other                 => Left(DecodingError(s"Can't build EntryType from input $other"))
    }

    implicit val encoder: ArgEncoder[EntryType] = new ArgEncoder[EntryType] {

      override def encode(value: EntryType): Value =
        value match {
          case EntryType.tree   => EnumValue("tree")
          case EntryType.blob   => EnumValue("blob")
          case EntryType.commit => EnumValue("commit")
        }

      override def typeName: String = "EntryType"
    }

  }

  sealed trait EpicSort extends scala.Product with scala.Serializable

  object EpicSort {
    case object start_date_desc extends EpicSort
    case object start_date_asc extends EpicSort
    case object end_date_desc extends EpicSort
    case object end_date_asc extends EpicSort

    implicit val decoder: ScalarDecoder[EpicSort] = {
      case StringValue("start_date_desc") => Right(EpicSort.start_date_desc)
      case StringValue("start_date_asc")  => Right(EpicSort.start_date_asc)
      case StringValue("end_date_desc")   => Right(EpicSort.end_date_desc)
      case StringValue("end_date_asc")    => Right(EpicSort.end_date_asc)
      case other                          => Left(DecodingError(s"Can't build EpicSort from input $other"))
    }

    implicit val encoder: ArgEncoder[EpicSort] = new ArgEncoder[EpicSort] {

      override def encode(value: EpicSort): Value =
        value match {
          case EpicSort.start_date_desc => EnumValue("start_date_desc")
          case EpicSort.start_date_asc  => EnumValue("start_date_asc")
          case EpicSort.end_date_desc   => EnumValue("end_date_desc")
          case EpicSort.end_date_asc    => EnumValue("end_date_asc")
        }

      override def typeName: String = "EpicSort"
    }

  }

  sealed trait EpicState extends scala.Product with scala.Serializable

  object EpicState {
    case object all extends EpicState
    case object opened extends EpicState
    case object closed extends EpicState

    implicit val decoder: ScalarDecoder[EpicState] = {
      case StringValue("all")    => Right(EpicState.all)
      case StringValue("opened") => Right(EpicState.opened)
      case StringValue("closed") => Right(EpicState.closed)
      case other                 => Left(DecodingError(s"Can't build EpicState from input $other"))
    }

    implicit val encoder: ArgEncoder[EpicState] = new ArgEncoder[EpicState] {

      override def encode(value: EpicState): Value =
        value match {
          case EpicState.all    => EnumValue("all")
          case EpicState.opened => EnumValue("opened")
          case EpicState.closed => EnumValue("closed")
        }

      override def typeName: String = "EpicState"
    }

  }

  sealed trait EpicStateEvent extends scala.Product with scala.Serializable

  object EpicStateEvent {
    case object REOPEN extends EpicStateEvent
    case object CLOSE extends EpicStateEvent

    implicit val decoder: ScalarDecoder[EpicStateEvent] = {
      case StringValue("REOPEN") => Right(EpicStateEvent.REOPEN)
      case StringValue("CLOSE")  => Right(EpicStateEvent.CLOSE)
      case other                 => Left(DecodingError(s"Can't build EpicStateEvent from input $other"))
    }

    implicit val encoder: ArgEncoder[EpicStateEvent] = new ArgEncoder[EpicStateEvent] {

      override def encode(value: EpicStateEvent): Value =
        value match {
          case EpicStateEvent.REOPEN => EnumValue("REOPEN")
          case EpicStateEvent.CLOSE  => EnumValue("CLOSE")
        }

      override def typeName: String = "EpicStateEvent"
    }

  }

  sealed trait HealthStatus extends scala.Product with scala.Serializable

  object HealthStatus {
    case object onTrack extends HealthStatus
    case object needsAttention extends HealthStatus
    case object atRisk extends HealthStatus

    implicit val decoder: ScalarDecoder[HealthStatus] = {
      case StringValue("onTrack")        => Right(HealthStatus.onTrack)
      case StringValue("needsAttention") => Right(HealthStatus.needsAttention)
      case StringValue("atRisk")         => Right(HealthStatus.atRisk)
      case other                         => Left(DecodingError(s"Can't build HealthStatus from input $other"))
    }

    implicit val encoder: ArgEncoder[HealthStatus] = new ArgEncoder[HealthStatus] {

      override def encode(value: HealthStatus): Value =
        value match {
          case HealthStatus.onTrack        => EnumValue("onTrack")
          case HealthStatus.needsAttention => EnumValue("needsAttention")
          case HealthStatus.atRisk         => EnumValue("atRisk")
        }

      override def typeName: String = "HealthStatus"
    }

  }

  sealed trait IssuableState extends scala.Product with scala.Serializable

  object IssuableState {
    case object opened extends IssuableState
    case object closed extends IssuableState
    case object locked extends IssuableState

    implicit val decoder: ScalarDecoder[IssuableState] = {
      case StringValue("opened") => Right(IssuableState.opened)
      case StringValue("closed") => Right(IssuableState.closed)
      case StringValue("locked") => Right(IssuableState.locked)
      case other                 => Left(DecodingError(s"Can't build IssuableState from input $other"))
    }

    implicit val encoder: ArgEncoder[IssuableState] = new ArgEncoder[IssuableState] {

      override def encode(value: IssuableState): Value =
        value match {
          case IssuableState.opened => EnumValue("opened")
          case IssuableState.closed => EnumValue("closed")
          case IssuableState.locked => EnumValue("locked")
        }

      override def typeName: String = "IssuableState"
    }

  }

  sealed trait IssueSort extends scala.Product with scala.Serializable

  object IssueSort {
    case object updated_desc extends IssueSort
    case object updated_asc extends IssueSort
    case object created_desc extends IssueSort
    case object created_asc extends IssueSort
    case object PRIORITY_ASC extends IssueSort
    case object PRIORITY_DESC extends IssueSort
    case object LABEL_PRIORITY_ASC extends IssueSort
    case object LABEL_PRIORITY_DESC extends IssueSort
    case object MILESTONE_DUE_ASC extends IssueSort
    case object MILESTONE_DUE_DESC extends IssueSort
    case object DUE_DATE_ASC extends IssueSort
    case object DUE_DATE_DESC extends IssueSort
    case object RELATIVE_POSITION_ASC extends IssueSort
    case object WEIGHT_ASC extends IssueSort
    case object WEIGHT_DESC extends IssueSort

    implicit val decoder: ScalarDecoder[IssueSort] = {
      case StringValue("updated_desc")          => Right(IssueSort.updated_desc)
      case StringValue("updated_asc")           => Right(IssueSort.updated_asc)
      case StringValue("created_desc")          => Right(IssueSort.created_desc)
      case StringValue("created_asc")           => Right(IssueSort.created_asc)
      case StringValue("PRIORITY_ASC")          => Right(IssueSort.PRIORITY_ASC)
      case StringValue("PRIORITY_DESC")         => Right(IssueSort.PRIORITY_DESC)
      case StringValue("LABEL_PRIORITY_ASC")    => Right(IssueSort.LABEL_PRIORITY_ASC)
      case StringValue("LABEL_PRIORITY_DESC")   => Right(IssueSort.LABEL_PRIORITY_DESC)
      case StringValue("MILESTONE_DUE_ASC")     => Right(IssueSort.MILESTONE_DUE_ASC)
      case StringValue("MILESTONE_DUE_DESC")    => Right(IssueSort.MILESTONE_DUE_DESC)
      case StringValue("DUE_DATE_ASC")          => Right(IssueSort.DUE_DATE_ASC)
      case StringValue("DUE_DATE_DESC")         => Right(IssueSort.DUE_DATE_DESC)
      case StringValue("RELATIVE_POSITION_ASC") => Right(IssueSort.RELATIVE_POSITION_ASC)
      case StringValue("WEIGHT_ASC")            => Right(IssueSort.WEIGHT_ASC)
      case StringValue("WEIGHT_DESC")           => Right(IssueSort.WEIGHT_DESC)
      case other                                => Left(DecodingError(s"Can't build IssueSort from input $other"))
    }

    implicit val encoder: ArgEncoder[IssueSort] = new ArgEncoder[IssueSort] {

      override def encode(value: IssueSort): Value =
        value match {
          case IssueSort.updated_desc          => EnumValue("updated_desc")
          case IssueSort.updated_asc           => EnumValue("updated_asc")
          case IssueSort.created_desc          => EnumValue("created_desc")
          case IssueSort.created_asc           => EnumValue("created_asc")
          case IssueSort.PRIORITY_ASC          => EnumValue("PRIORITY_ASC")
          case IssueSort.PRIORITY_DESC         => EnumValue("PRIORITY_DESC")
          case IssueSort.LABEL_PRIORITY_ASC    => EnumValue("LABEL_PRIORITY_ASC")
          case IssueSort.LABEL_PRIORITY_DESC   => EnumValue("LABEL_PRIORITY_DESC")
          case IssueSort.MILESTONE_DUE_ASC     => EnumValue("MILESTONE_DUE_ASC")
          case IssueSort.MILESTONE_DUE_DESC    => EnumValue("MILESTONE_DUE_DESC")
          case IssueSort.DUE_DATE_ASC          => EnumValue("DUE_DATE_ASC")
          case IssueSort.DUE_DATE_DESC         => EnumValue("DUE_DATE_DESC")
          case IssueSort.RELATIVE_POSITION_ASC => EnumValue("RELATIVE_POSITION_ASC")
          case IssueSort.WEIGHT_ASC            => EnumValue("WEIGHT_ASC")
          case IssueSort.WEIGHT_DESC           => EnumValue("WEIGHT_DESC")
        }

      override def typeName: String = "IssueSort"
    }

  }

  sealed trait IssueState extends scala.Product with scala.Serializable

  object IssueState {
    case object opened extends IssueState
    case object closed extends IssueState
    case object locked extends IssueState

    implicit val decoder: ScalarDecoder[IssueState] = {
      case StringValue("opened") => Right(IssueState.opened)
      case StringValue("closed") => Right(IssueState.closed)
      case StringValue("locked") => Right(IssueState.locked)
      case other                 => Left(DecodingError(s"Can't build IssueState from input $other"))
    }

    implicit val encoder: ArgEncoder[IssueState] = new ArgEncoder[IssueState] {

      override def encode(value: IssueState): Value =
        value match {
          case IssueState.opened => EnumValue("opened")
          case IssueState.closed => EnumValue("closed")
          case IssueState.locked => EnumValue("locked")
        }

      override def typeName: String = "IssueState"
    }

  }

  sealed trait IterationState extends scala.Product with scala.Serializable

  object IterationState {
    case object upcoming extends IterationState
    case object started extends IterationState
    case object opened extends IterationState
    case object closed extends IterationState
    case object all extends IterationState

    implicit val decoder: ScalarDecoder[IterationState] = {
      case StringValue("upcoming") => Right(IterationState.upcoming)
      case StringValue("started")  => Right(IterationState.started)
      case StringValue("opened")   => Right(IterationState.opened)
      case StringValue("closed")   => Right(IterationState.closed)
      case StringValue("all")      => Right(IterationState.all)
      case other                   => Left(DecodingError(s"Can't build IterationState from input $other"))
    }

    implicit val encoder: ArgEncoder[IterationState] = new ArgEncoder[IterationState] {

      override def encode(value: IterationState): Value =
        value match {
          case IterationState.upcoming => EnumValue("upcoming")
          case IterationState.started  => EnumValue("started")
          case IterationState.opened   => EnumValue("opened")
          case IterationState.closed   => EnumValue("closed")
          case IterationState.all      => EnumValue("all")
        }

      override def typeName: String = "IterationState"
    }

  }

  sealed trait ListLimitMetric extends scala.Product with scala.Serializable

  object ListLimitMetric {
    case object all_metrics extends ListLimitMetric
    case object issue_count extends ListLimitMetric
    case object issue_weights extends ListLimitMetric

    implicit val decoder: ScalarDecoder[ListLimitMetric] = {
      case StringValue("all_metrics")   => Right(ListLimitMetric.all_metrics)
      case StringValue("issue_count")   => Right(ListLimitMetric.issue_count)
      case StringValue("issue_weights") => Right(ListLimitMetric.issue_weights)
      case other                        => Left(DecodingError(s"Can't build ListLimitMetric from input $other"))
    }

    implicit val encoder: ArgEncoder[ListLimitMetric] = new ArgEncoder[ListLimitMetric] {

      override def encode(value: ListLimitMetric): Value =
        value match {
          case ListLimitMetric.all_metrics   => EnumValue("all_metrics")
          case ListLimitMetric.issue_count   => EnumValue("issue_count")
          case ListLimitMetric.issue_weights => EnumValue("issue_weights")
        }

      override def typeName: String = "ListLimitMetric"
    }

  }

  sealed trait MergeRequestState extends scala.Product with scala.Serializable

  object MergeRequestState {
    case object opened extends MergeRequestState
    case object closed extends MergeRequestState
    case object locked extends MergeRequestState
    case object merged extends MergeRequestState

    implicit val decoder: ScalarDecoder[MergeRequestState] = {
      case StringValue("opened") => Right(MergeRequestState.opened)
      case StringValue("closed") => Right(MergeRequestState.closed)
      case StringValue("locked") => Right(MergeRequestState.locked)
      case StringValue("merged") => Right(MergeRequestState.merged)
      case other                 => Left(DecodingError(s"Can't build MergeRequestState from input $other"))
    }

    implicit val encoder: ArgEncoder[MergeRequestState] = new ArgEncoder[MergeRequestState] {

      override def encode(value: MergeRequestState): Value =
        value match {
          case MergeRequestState.opened => EnumValue("opened")
          case MergeRequestState.closed => EnumValue("closed")
          case MergeRequestState.locked => EnumValue("locked")
          case MergeRequestState.merged => EnumValue("merged")
        }

      override def typeName: String = "MergeRequestState"
    }

  }

  sealed trait MilestoneStateEnum extends scala.Product with scala.Serializable

  object MilestoneStateEnum {
    case object active extends MilestoneStateEnum
    case object closed extends MilestoneStateEnum

    implicit val decoder: ScalarDecoder[MilestoneStateEnum] = {
      case StringValue("active") => Right(MilestoneStateEnum.active)
      case StringValue("closed") => Right(MilestoneStateEnum.closed)
      case other                 => Left(DecodingError(s"Can't build MilestoneStateEnum from input $other"))
    }

    implicit val encoder: ArgEncoder[MilestoneStateEnum] = new ArgEncoder[MilestoneStateEnum] {

      override def encode(value: MilestoneStateEnum): Value =
        value match {
          case MilestoneStateEnum.active => EnumValue("active")
          case MilestoneStateEnum.closed => EnumValue("closed")
        }

      override def typeName: String = "MilestoneStateEnum"
    }

  }

  sealed trait MoveType extends scala.Product with scala.Serializable

  object MoveType {
    case object before extends MoveType
    case object after extends MoveType

    implicit val decoder: ScalarDecoder[MoveType] = {
      case StringValue("before") => Right(MoveType.before)
      case StringValue("after")  => Right(MoveType.after)
      case other                 => Left(DecodingError(s"Can't build MoveType from input $other"))
    }

    implicit val encoder: ArgEncoder[MoveType] = new ArgEncoder[MoveType] {

      override def encode(value: MoveType): Value =
        value match {
          case MoveType.before => EnumValue("before")
          case MoveType.after  => EnumValue("after")
        }

      override def typeName: String = "MoveType"
    }

  }

  sealed trait MutationOperationMode extends scala.Product with scala.Serializable

  object MutationOperationMode {
    case object REPLACE extends MutationOperationMode
    case object APPEND extends MutationOperationMode
    case object REMOVE extends MutationOperationMode

    implicit val decoder: ScalarDecoder[MutationOperationMode] = {
      case StringValue("REPLACE") => Right(MutationOperationMode.REPLACE)
      case StringValue("APPEND")  => Right(MutationOperationMode.APPEND)
      case StringValue("REMOVE")  => Right(MutationOperationMode.REMOVE)
      case other                  => Left(DecodingError(s"Can't build MutationOperationMode from input $other"))
    }

    implicit val encoder: ArgEncoder[MutationOperationMode] = new ArgEncoder[MutationOperationMode] {

      override def encode(value: MutationOperationMode): Value =
        value match {
          case MutationOperationMode.REPLACE => EnumValue("REPLACE")
          case MutationOperationMode.APPEND  => EnumValue("APPEND")
          case MutationOperationMode.REMOVE  => EnumValue("REMOVE")
        }

      override def typeName: String = "MutationOperationMode"
    }

  }

  sealed trait PackageTypeEnum extends scala.Product with scala.Serializable

  object PackageTypeEnum {
    case object MAVEN extends PackageTypeEnum
    case object NPM extends PackageTypeEnum
    case object CONAN extends PackageTypeEnum
    case object NUGET extends PackageTypeEnum
    case object PYPI extends PackageTypeEnum
    case object COMPOSER extends PackageTypeEnum

    implicit val decoder: ScalarDecoder[PackageTypeEnum] = {
      case StringValue("MAVEN")    => Right(PackageTypeEnum.MAVEN)
      case StringValue("NPM")      => Right(PackageTypeEnum.NPM)
      case StringValue("CONAN")    => Right(PackageTypeEnum.CONAN)
      case StringValue("NUGET")    => Right(PackageTypeEnum.NUGET)
      case StringValue("PYPI")     => Right(PackageTypeEnum.PYPI)
      case StringValue("COMPOSER") => Right(PackageTypeEnum.COMPOSER)
      case other                   => Left(DecodingError(s"Can't build PackageTypeEnum from input $other"))
    }

    implicit val encoder: ArgEncoder[PackageTypeEnum] = new ArgEncoder[PackageTypeEnum] {

      override def encode(value: PackageTypeEnum): Value =
        value match {
          case PackageTypeEnum.MAVEN    => EnumValue("MAVEN")
          case PackageTypeEnum.NPM      => EnumValue("NPM")
          case PackageTypeEnum.CONAN    => EnumValue("CONAN")
          case PackageTypeEnum.NUGET    => EnumValue("NUGET")
          case PackageTypeEnum.PYPI     => EnumValue("PYPI")
          case PackageTypeEnum.COMPOSER => EnumValue("COMPOSER")
        }

      override def typeName: String = "PackageTypeEnum"
    }

  }

  sealed trait PipelineStatusEnum extends scala.Product with scala.Serializable

  object PipelineStatusEnum {
    case object CREATED extends PipelineStatusEnum
    case object WAITING_FOR_RESOURCE extends PipelineStatusEnum
    case object PREPARING extends PipelineStatusEnum
    case object PENDING extends PipelineStatusEnum
    case object RUNNING extends PipelineStatusEnum
    case object FAILED extends PipelineStatusEnum
    case object SUCCESS extends PipelineStatusEnum
    case object CANCELED extends PipelineStatusEnum
    case object SKIPPED extends PipelineStatusEnum
    case object MANUAL extends PipelineStatusEnum
    case object SCHEDULED extends PipelineStatusEnum

    implicit val decoder: ScalarDecoder[PipelineStatusEnum] = {
      case StringValue("CREATED")              => Right(PipelineStatusEnum.CREATED)
      case StringValue("WAITING_FOR_RESOURCE") => Right(PipelineStatusEnum.WAITING_FOR_RESOURCE)
      case StringValue("PREPARING")            => Right(PipelineStatusEnum.PREPARING)
      case StringValue("PENDING")              => Right(PipelineStatusEnum.PENDING)
      case StringValue("RUNNING")              => Right(PipelineStatusEnum.RUNNING)
      case StringValue("FAILED")               => Right(PipelineStatusEnum.FAILED)
      case StringValue("SUCCESS")              => Right(PipelineStatusEnum.SUCCESS)
      case StringValue("CANCELED")             => Right(PipelineStatusEnum.CANCELED)
      case StringValue("SKIPPED")              => Right(PipelineStatusEnum.SKIPPED)
      case StringValue("MANUAL")               => Right(PipelineStatusEnum.MANUAL)
      case StringValue("SCHEDULED")            => Right(PipelineStatusEnum.SCHEDULED)
      case other                               => Left(DecodingError(s"Can't build PipelineStatusEnum from input $other"))
    }

    implicit val encoder: ArgEncoder[PipelineStatusEnum] = new ArgEncoder[PipelineStatusEnum] {

      override def encode(value: PipelineStatusEnum): Value =
        value match {
          case PipelineStatusEnum.CREATED              => EnumValue("CREATED")
          case PipelineStatusEnum.WAITING_FOR_RESOURCE => EnumValue("WAITING_FOR_RESOURCE")
          case PipelineStatusEnum.PREPARING            => EnumValue("PREPARING")
          case PipelineStatusEnum.PENDING              => EnumValue("PENDING")
          case PipelineStatusEnum.RUNNING              => EnumValue("RUNNING")
          case PipelineStatusEnum.FAILED               => EnumValue("FAILED")
          case PipelineStatusEnum.SUCCESS              => EnumValue("SUCCESS")
          case PipelineStatusEnum.CANCELED             => EnumValue("CANCELED")
          case PipelineStatusEnum.SKIPPED              => EnumValue("SKIPPED")
          case PipelineStatusEnum.MANUAL               => EnumValue("MANUAL")
          case PipelineStatusEnum.SCHEDULED            => EnumValue("SCHEDULED")
        }

      override def typeName: String = "PipelineStatusEnum"
    }

  }

  sealed trait ProjectSettingEnum extends scala.Product with scala.Serializable

  object ProjectSettingEnum {
    case object gdpr extends ProjectSettingEnum
    case object hipaa extends ProjectSettingEnum
    case object pci_dss extends ProjectSettingEnum
    case object soc_2 extends ProjectSettingEnum
    case object sox extends ProjectSettingEnum

    implicit val decoder: ScalarDecoder[ProjectSettingEnum] = {
      case StringValue("gdpr")    => Right(ProjectSettingEnum.gdpr)
      case StringValue("hipaa")   => Right(ProjectSettingEnum.hipaa)
      case StringValue("pci_dss") => Right(ProjectSettingEnum.pci_dss)
      case StringValue("soc_2")   => Right(ProjectSettingEnum.soc_2)
      case StringValue("sox")     => Right(ProjectSettingEnum.sox)
      case other                  => Left(DecodingError(s"Can't build ProjectSettingEnum from input $other"))
    }

    implicit val encoder: ArgEncoder[ProjectSettingEnum] = new ArgEncoder[ProjectSettingEnum] {

      override def encode(value: ProjectSettingEnum): Value =
        value match {
          case ProjectSettingEnum.gdpr    => EnumValue("gdpr")
          case ProjectSettingEnum.hipaa   => EnumValue("hipaa")
          case ProjectSettingEnum.pci_dss => EnumValue("pci_dss")
          case ProjectSettingEnum.soc_2   => EnumValue("soc_2")
          case ProjectSettingEnum.sox     => EnumValue("sox")
        }

      override def typeName: String = "ProjectSettingEnum"
    }

  }

  sealed trait ReleaseAssetLinkType extends scala.Product with scala.Serializable

  object ReleaseAssetLinkType {
    case object OTHER extends ReleaseAssetLinkType
    case object RUNBOOK extends ReleaseAssetLinkType
    case object PACKAGE extends ReleaseAssetLinkType
    case object IMAGE extends ReleaseAssetLinkType

    implicit val decoder: ScalarDecoder[ReleaseAssetLinkType] = {
      case StringValue("OTHER")   => Right(ReleaseAssetLinkType.OTHER)
      case StringValue("RUNBOOK") => Right(ReleaseAssetLinkType.RUNBOOK)
      case StringValue("PACKAGE") => Right(ReleaseAssetLinkType.PACKAGE)
      case StringValue("IMAGE")   => Right(ReleaseAssetLinkType.IMAGE)
      case other                  => Left(DecodingError(s"Can't build ReleaseAssetLinkType from input $other"))
    }

    implicit val encoder: ArgEncoder[ReleaseAssetLinkType] = new ArgEncoder[ReleaseAssetLinkType] {

      override def encode(value: ReleaseAssetLinkType): Value =
        value match {
          case ReleaseAssetLinkType.OTHER   => EnumValue("OTHER")
          case ReleaseAssetLinkType.RUNBOOK => EnumValue("RUNBOOK")
          case ReleaseAssetLinkType.PACKAGE => EnumValue("PACKAGE")
          case ReleaseAssetLinkType.IMAGE   => EnumValue("IMAGE")
        }

      override def typeName: String = "ReleaseAssetLinkType"
    }

  }

  sealed trait RequirementState extends scala.Product with scala.Serializable

  object RequirementState {
    case object OPENED extends RequirementState
    case object ARCHIVED extends RequirementState

    implicit val decoder: ScalarDecoder[RequirementState] = {
      case StringValue("OPENED")   => Right(RequirementState.OPENED)
      case StringValue("ARCHIVED") => Right(RequirementState.ARCHIVED)
      case other                   => Left(DecodingError(s"Can't build RequirementState from input $other"))
    }

    implicit val encoder: ArgEncoder[RequirementState] = new ArgEncoder[RequirementState] {

      override def encode(value: RequirementState): Value =
        value match {
          case RequirementState.OPENED   => EnumValue("OPENED")
          case RequirementState.ARCHIVED => EnumValue("ARCHIVED")
        }

      override def typeName: String = "RequirementState"
    }

  }

  sealed trait SecurityScannerType extends scala.Product with scala.Serializable

  object SecurityScannerType {
    case object SAST extends SecurityScannerType
    case object DAST extends SecurityScannerType
    case object DEPENDENCY_SCANNING extends SecurityScannerType
    case object CONTAINER_SCANNING extends SecurityScannerType
    case object SECRET_DETECTION extends SecurityScannerType
    case object COVERAGE_FUZZING extends SecurityScannerType

    implicit val decoder: ScalarDecoder[SecurityScannerType] = {
      case StringValue("SAST")                => Right(SecurityScannerType.SAST)
      case StringValue("DAST")                => Right(SecurityScannerType.DAST)
      case StringValue("DEPENDENCY_SCANNING") => Right(SecurityScannerType.DEPENDENCY_SCANNING)
      case StringValue("CONTAINER_SCANNING")  => Right(SecurityScannerType.CONTAINER_SCANNING)
      case StringValue("SECRET_DETECTION")    => Right(SecurityScannerType.SECRET_DETECTION)
      case StringValue("COVERAGE_FUZZING")    => Right(SecurityScannerType.COVERAGE_FUZZING)
      case other                              => Left(DecodingError(s"Can't build SecurityScannerType from input $other"))
    }

    implicit val encoder: ArgEncoder[SecurityScannerType] = new ArgEncoder[SecurityScannerType] {

      override def encode(value: SecurityScannerType): Value =
        value match {
          case SecurityScannerType.SAST                => EnumValue("SAST")
          case SecurityScannerType.DAST                => EnumValue("DAST")
          case SecurityScannerType.DEPENDENCY_SCANNING => EnumValue("DEPENDENCY_SCANNING")
          case SecurityScannerType.CONTAINER_SCANNING  => EnumValue("CONTAINER_SCANNING")
          case SecurityScannerType.SECRET_DETECTION    => EnumValue("SECRET_DETECTION")
          case SecurityScannerType.COVERAGE_FUZZING    => EnumValue("COVERAGE_FUZZING")
        }

      override def typeName: String = "SecurityScannerType"
    }

  }

  sealed trait SentryErrorStatus extends scala.Product with scala.Serializable

  object SentryErrorStatus {
    case object RESOLVED extends SentryErrorStatus
    case object RESOLVED_IN_NEXT_RELEASE extends SentryErrorStatus
    case object UNRESOLVED extends SentryErrorStatus
    case object IGNORED extends SentryErrorStatus

    implicit val decoder: ScalarDecoder[SentryErrorStatus] = {
      case StringValue("RESOLVED")                 => Right(SentryErrorStatus.RESOLVED)
      case StringValue("RESOLVED_IN_NEXT_RELEASE") => Right(SentryErrorStatus.RESOLVED_IN_NEXT_RELEASE)
      case StringValue("UNRESOLVED")               => Right(SentryErrorStatus.UNRESOLVED)
      case StringValue("IGNORED")                  => Right(SentryErrorStatus.IGNORED)
      case other                                   => Left(DecodingError(s"Can't build SentryErrorStatus from input $other"))
    }

    implicit val encoder: ArgEncoder[SentryErrorStatus] = new ArgEncoder[SentryErrorStatus] {

      override def encode(value: SentryErrorStatus): Value =
        value match {
          case SentryErrorStatus.RESOLVED                 => EnumValue("RESOLVED")
          case SentryErrorStatus.RESOLVED_IN_NEXT_RELEASE => EnumValue("RESOLVED_IN_NEXT_RELEASE")
          case SentryErrorStatus.UNRESOLVED               => EnumValue("UNRESOLVED")
          case SentryErrorStatus.IGNORED                  => EnumValue("IGNORED")
        }

      override def typeName: String = "SentryErrorStatus"
    }

  }

  sealed trait ServiceType extends scala.Product with scala.Serializable

  object ServiceType {
    case object ALERTS_SERVICE extends ServiceType
    case object ASANA_SERVICE extends ServiceType
    case object ASSEMBLA_SERVICE extends ServiceType
    case object BAMBOO_SERVICE extends ServiceType
    case object BUGZILLA_SERVICE extends ServiceType
    case object BUILDKITE_SERVICE extends ServiceType
    case object CAMPFIRE_SERVICE extends ServiceType
    case object CONFLUENCE_SERVICE extends ServiceType
    case object CUSTOM_ISSUE_TRACKER_SERVICE extends ServiceType
    case object DISCORD_SERVICE extends ServiceType
    case object DRONE_CI_SERVICE extends ServiceType
    case object EMAILS_ON_PUSH_SERVICE extends ServiceType
    case object EXTERNAL_WIKI_SERVICE extends ServiceType
    case object FLOWDOCK_SERVICE extends ServiceType
    case object HANGOUTS_CHAT_SERVICE extends ServiceType
    case object HIPCHAT_SERVICE extends ServiceType
    case object IRKER_SERVICE extends ServiceType
    case object JIRA_SERVICE extends ServiceType
    case object MATTERMOST_SERVICE extends ServiceType
    case object MATTERMOST_SLASH_COMMANDS_SERVICE extends ServiceType
    case object MICROSOFT_TEAMS_SERVICE extends ServiceType
    case object PACKAGIST_SERVICE extends ServiceType
    case object PIPELINES_EMAIL_SERVICE extends ServiceType
    case object PIVOTALTRACKER_SERVICE extends ServiceType
    case object PROMETHEUS_SERVICE extends ServiceType
    case object PUSHOVER_SERVICE extends ServiceType
    case object REDMINE_SERVICE extends ServiceType
    case object SLACK_SERVICE extends ServiceType
    case object SLACK_SLASH_COMMANDS_SERVICE extends ServiceType
    case object TEAMCITY_SERVICE extends ServiceType
    case object UNIFY_CIRCUIT_SERVICE extends ServiceType
    case object WEBEX_TEAMS_SERVICE extends ServiceType
    case object YOUTRACK_SERVICE extends ServiceType
    case object GITHUB_SERVICE extends ServiceType
    case object JENKINS_SERVICE extends ServiceType

    implicit val decoder: ScalarDecoder[ServiceType] = {
      case StringValue("ALERTS_SERVICE")                    => Right(ServiceType.ALERTS_SERVICE)
      case StringValue("ASANA_SERVICE")                     => Right(ServiceType.ASANA_SERVICE)
      case StringValue("ASSEMBLA_SERVICE")                  => Right(ServiceType.ASSEMBLA_SERVICE)
      case StringValue("BAMBOO_SERVICE")                    => Right(ServiceType.BAMBOO_SERVICE)
      case StringValue("BUGZILLA_SERVICE")                  => Right(ServiceType.BUGZILLA_SERVICE)
      case StringValue("BUILDKITE_SERVICE")                 => Right(ServiceType.BUILDKITE_SERVICE)
      case StringValue("CAMPFIRE_SERVICE")                  => Right(ServiceType.CAMPFIRE_SERVICE)
      case StringValue("CONFLUENCE_SERVICE")                => Right(ServiceType.CONFLUENCE_SERVICE)
      case StringValue("CUSTOM_ISSUE_TRACKER_SERVICE")      => Right(ServiceType.CUSTOM_ISSUE_TRACKER_SERVICE)
      case StringValue("DISCORD_SERVICE")                   => Right(ServiceType.DISCORD_SERVICE)
      case StringValue("DRONE_CI_SERVICE")                  => Right(ServiceType.DRONE_CI_SERVICE)
      case StringValue("EMAILS_ON_PUSH_SERVICE")            => Right(ServiceType.EMAILS_ON_PUSH_SERVICE)
      case StringValue("EXTERNAL_WIKI_SERVICE")             => Right(ServiceType.EXTERNAL_WIKI_SERVICE)
      case StringValue("FLOWDOCK_SERVICE")                  => Right(ServiceType.FLOWDOCK_SERVICE)
      case StringValue("HANGOUTS_CHAT_SERVICE")             => Right(ServiceType.HANGOUTS_CHAT_SERVICE)
      case StringValue("HIPCHAT_SERVICE")                   => Right(ServiceType.HIPCHAT_SERVICE)
      case StringValue("IRKER_SERVICE")                     => Right(ServiceType.IRKER_SERVICE)
      case StringValue("JIRA_SERVICE")                      => Right(ServiceType.JIRA_SERVICE)
      case StringValue("MATTERMOST_SERVICE")                => Right(ServiceType.MATTERMOST_SERVICE)
      case StringValue("MATTERMOST_SLASH_COMMANDS_SERVICE") => Right(ServiceType.MATTERMOST_SLASH_COMMANDS_SERVICE)
      case StringValue("MICROSOFT_TEAMS_SERVICE")           => Right(ServiceType.MICROSOFT_TEAMS_SERVICE)
      case StringValue("PACKAGIST_SERVICE")                 => Right(ServiceType.PACKAGIST_SERVICE)
      case StringValue("PIPELINES_EMAIL_SERVICE")           => Right(ServiceType.PIPELINES_EMAIL_SERVICE)
      case StringValue("PIVOTALTRACKER_SERVICE")            => Right(ServiceType.PIVOTALTRACKER_SERVICE)
      case StringValue("PROMETHEUS_SERVICE")                => Right(ServiceType.PROMETHEUS_SERVICE)
      case StringValue("PUSHOVER_SERVICE")                  => Right(ServiceType.PUSHOVER_SERVICE)
      case StringValue("REDMINE_SERVICE")                   => Right(ServiceType.REDMINE_SERVICE)
      case StringValue("SLACK_SERVICE")                     => Right(ServiceType.SLACK_SERVICE)
      case StringValue("SLACK_SLASH_COMMANDS_SERVICE")      => Right(ServiceType.SLACK_SLASH_COMMANDS_SERVICE)
      case StringValue("TEAMCITY_SERVICE")                  => Right(ServiceType.TEAMCITY_SERVICE)
      case StringValue("UNIFY_CIRCUIT_SERVICE")             => Right(ServiceType.UNIFY_CIRCUIT_SERVICE)
      case StringValue("WEBEX_TEAMS_SERVICE")               => Right(ServiceType.WEBEX_TEAMS_SERVICE)
      case StringValue("YOUTRACK_SERVICE")                  => Right(ServiceType.YOUTRACK_SERVICE)
      case StringValue("GITHUB_SERVICE")                    => Right(ServiceType.GITHUB_SERVICE)
      case StringValue("JENKINS_SERVICE")                   => Right(ServiceType.JENKINS_SERVICE)
      case other                                            => Left(DecodingError(s"Can't build ServiceType from input $other"))
    }

    implicit val encoder: ArgEncoder[ServiceType] = new ArgEncoder[ServiceType] {

      override def encode(value: ServiceType): Value =
        value match {
          case ServiceType.ALERTS_SERVICE                    => EnumValue("ALERTS_SERVICE")
          case ServiceType.ASANA_SERVICE                     => EnumValue("ASANA_SERVICE")
          case ServiceType.ASSEMBLA_SERVICE                  => EnumValue("ASSEMBLA_SERVICE")
          case ServiceType.BAMBOO_SERVICE                    => EnumValue("BAMBOO_SERVICE")
          case ServiceType.BUGZILLA_SERVICE                  => EnumValue("BUGZILLA_SERVICE")
          case ServiceType.BUILDKITE_SERVICE                 => EnumValue("BUILDKITE_SERVICE")
          case ServiceType.CAMPFIRE_SERVICE                  => EnumValue("CAMPFIRE_SERVICE")
          case ServiceType.CONFLUENCE_SERVICE                => EnumValue("CONFLUENCE_SERVICE")
          case ServiceType.CUSTOM_ISSUE_TRACKER_SERVICE      => EnumValue("CUSTOM_ISSUE_TRACKER_SERVICE")
          case ServiceType.DISCORD_SERVICE                   => EnumValue("DISCORD_SERVICE")
          case ServiceType.DRONE_CI_SERVICE                  => EnumValue("DRONE_CI_SERVICE")
          case ServiceType.EMAILS_ON_PUSH_SERVICE            => EnumValue("EMAILS_ON_PUSH_SERVICE")
          case ServiceType.EXTERNAL_WIKI_SERVICE             => EnumValue("EXTERNAL_WIKI_SERVICE")
          case ServiceType.FLOWDOCK_SERVICE                  => EnumValue("FLOWDOCK_SERVICE")
          case ServiceType.HANGOUTS_CHAT_SERVICE             => EnumValue("HANGOUTS_CHAT_SERVICE")
          case ServiceType.HIPCHAT_SERVICE                   => EnumValue("HIPCHAT_SERVICE")
          case ServiceType.IRKER_SERVICE                     => EnumValue("IRKER_SERVICE")
          case ServiceType.JIRA_SERVICE                      => EnumValue("JIRA_SERVICE")
          case ServiceType.MATTERMOST_SERVICE                => EnumValue("MATTERMOST_SERVICE")
          case ServiceType.MATTERMOST_SLASH_COMMANDS_SERVICE => EnumValue("MATTERMOST_SLASH_COMMANDS_SERVICE")
          case ServiceType.MICROSOFT_TEAMS_SERVICE           => EnumValue("MICROSOFT_TEAMS_SERVICE")
          case ServiceType.PACKAGIST_SERVICE                 => EnumValue("PACKAGIST_SERVICE")
          case ServiceType.PIPELINES_EMAIL_SERVICE           => EnumValue("PIPELINES_EMAIL_SERVICE")
          case ServiceType.PIVOTALTRACKER_SERVICE            => EnumValue("PIVOTALTRACKER_SERVICE")
          case ServiceType.PROMETHEUS_SERVICE                => EnumValue("PROMETHEUS_SERVICE")
          case ServiceType.PUSHOVER_SERVICE                  => EnumValue("PUSHOVER_SERVICE")
          case ServiceType.REDMINE_SERVICE                   => EnumValue("REDMINE_SERVICE")
          case ServiceType.SLACK_SERVICE                     => EnumValue("SLACK_SERVICE")
          case ServiceType.SLACK_SLASH_COMMANDS_SERVICE      => EnumValue("SLACK_SLASH_COMMANDS_SERVICE")
          case ServiceType.TEAMCITY_SERVICE                  => EnumValue("TEAMCITY_SERVICE")
          case ServiceType.UNIFY_CIRCUIT_SERVICE             => EnumValue("UNIFY_CIRCUIT_SERVICE")
          case ServiceType.WEBEX_TEAMS_SERVICE               => EnumValue("WEBEX_TEAMS_SERVICE")
          case ServiceType.YOUTRACK_SERVICE                  => EnumValue("YOUTRACK_SERVICE")
          case ServiceType.GITHUB_SERVICE                    => EnumValue("GITHUB_SERVICE")
          case ServiceType.JENKINS_SERVICE                   => EnumValue("JENKINS_SERVICE")
        }

      override def typeName: String = "ServiceType"
    }

  }

  sealed trait SnippetBlobActionEnum extends scala.Product with scala.Serializable

  object SnippetBlobActionEnum {
    case object create extends SnippetBlobActionEnum
    case object update extends SnippetBlobActionEnum
    case object delete extends SnippetBlobActionEnum
    case object move extends SnippetBlobActionEnum

    implicit val decoder: ScalarDecoder[SnippetBlobActionEnum] = {
      case StringValue("create") => Right(SnippetBlobActionEnum.create)
      case StringValue("update") => Right(SnippetBlobActionEnum.update)
      case StringValue("delete") => Right(SnippetBlobActionEnum.delete)
      case StringValue("move")   => Right(SnippetBlobActionEnum.move)
      case other                 => Left(DecodingError(s"Can't build SnippetBlobActionEnum from input $other"))
    }

    implicit val encoder: ArgEncoder[SnippetBlobActionEnum] = new ArgEncoder[SnippetBlobActionEnum] {

      override def encode(value: SnippetBlobActionEnum): Value =
        value match {
          case SnippetBlobActionEnum.create => EnumValue("create")
          case SnippetBlobActionEnum.update => EnumValue("update")
          case SnippetBlobActionEnum.delete => EnumValue("delete")
          case SnippetBlobActionEnum.move   => EnumValue("move")
        }

      override def typeName: String = "SnippetBlobActionEnum"
    }

  }

  sealed trait Sort extends scala.Product with scala.Serializable

  object Sort {
    case object updated_desc extends Sort
    case object updated_asc extends Sort
    case object created_desc extends Sort
    case object created_asc extends Sort

    implicit val decoder: ScalarDecoder[Sort] = {
      case StringValue("updated_desc") => Right(Sort.updated_desc)
      case StringValue("updated_asc")  => Right(Sort.updated_asc)
      case StringValue("created_desc") => Right(Sort.created_desc)
      case StringValue("created_asc")  => Right(Sort.created_asc)
      case other                       => Left(DecodingError(s"Can't build Sort from input $other"))
    }

    implicit val encoder: ArgEncoder[Sort] = new ArgEncoder[Sort] {

      override def encode(value: Sort): Value =
        value match {
          case Sort.updated_desc => EnumValue("updated_desc")
          case Sort.updated_asc  => EnumValue("updated_asc")
          case Sort.created_desc => EnumValue("created_desc")
          case Sort.created_asc  => EnumValue("created_asc")
        }

      override def typeName: String = "Sort"
    }

  }

  sealed trait TestReportState extends scala.Product with scala.Serializable

  object TestReportState {
    case object PASSED extends TestReportState
    case object FAILED extends TestReportState

    implicit val decoder: ScalarDecoder[TestReportState] = {
      case StringValue("PASSED") => Right(TestReportState.PASSED)
      case StringValue("FAILED") => Right(TestReportState.FAILED)
      case other                 => Left(DecodingError(s"Can't build TestReportState from input $other"))
    }

    implicit val encoder: ArgEncoder[TestReportState] = new ArgEncoder[TestReportState] {

      override def encode(value: TestReportState): Value =
        value match {
          case TestReportState.PASSED => EnumValue("PASSED")
          case TestReportState.FAILED => EnumValue("FAILED")
        }

      override def typeName: String = "TestReportState"
    }

  }

  sealed trait TodoActionEnum extends scala.Product with scala.Serializable

  object TodoActionEnum {
    case object assigned extends TodoActionEnum
    case object mentioned extends TodoActionEnum
    case object build_failed extends TodoActionEnum
    case object marked extends TodoActionEnum
    case object approval_required extends TodoActionEnum
    case object unmergeable extends TodoActionEnum
    case object directly_addressed extends TodoActionEnum

    implicit val decoder: ScalarDecoder[TodoActionEnum] = {
      case StringValue("assigned")           => Right(TodoActionEnum.assigned)
      case StringValue("mentioned")          => Right(TodoActionEnum.mentioned)
      case StringValue("build_failed")       => Right(TodoActionEnum.build_failed)
      case StringValue("marked")             => Right(TodoActionEnum.marked)
      case StringValue("approval_required")  => Right(TodoActionEnum.approval_required)
      case StringValue("unmergeable")        => Right(TodoActionEnum.unmergeable)
      case StringValue("directly_addressed") => Right(TodoActionEnum.directly_addressed)
      case other                             => Left(DecodingError(s"Can't build TodoActionEnum from input $other"))
    }

    implicit val encoder: ArgEncoder[TodoActionEnum] = new ArgEncoder[TodoActionEnum] {

      override def encode(value: TodoActionEnum): Value =
        value match {
          case TodoActionEnum.assigned           => EnumValue("assigned")
          case TodoActionEnum.mentioned          => EnumValue("mentioned")
          case TodoActionEnum.build_failed       => EnumValue("build_failed")
          case TodoActionEnum.marked             => EnumValue("marked")
          case TodoActionEnum.approval_required  => EnumValue("approval_required")
          case TodoActionEnum.unmergeable        => EnumValue("unmergeable")
          case TodoActionEnum.directly_addressed => EnumValue("directly_addressed")
        }

      override def typeName: String = "TodoActionEnum"
    }

  }

  sealed trait TodoStateEnum extends scala.Product with scala.Serializable

  object TodoStateEnum {
    case object pending extends TodoStateEnum
    case object done extends TodoStateEnum

    implicit val decoder: ScalarDecoder[TodoStateEnum] = {
      case StringValue("pending") => Right(TodoStateEnum.pending)
      case StringValue("done")    => Right(TodoStateEnum.done)
      case other                  => Left(DecodingError(s"Can't build TodoStateEnum from input $other"))
    }

    implicit val encoder: ArgEncoder[TodoStateEnum] = new ArgEncoder[TodoStateEnum] {

      override def encode(value: TodoStateEnum): Value =
        value match {
          case TodoStateEnum.pending => EnumValue("pending")
          case TodoStateEnum.done    => EnumValue("done")
        }

      override def typeName: String = "TodoStateEnum"
    }

  }

  sealed trait TodoTargetEnum extends scala.Product with scala.Serializable

  object TodoTargetEnum {
    case object COMMIT extends TodoTargetEnum
    case object ISSUE extends TodoTargetEnum
    case object MERGEREQUEST extends TodoTargetEnum
    case object DESIGN extends TodoTargetEnum
    case object ALERT extends TodoTargetEnum
    case object EPIC extends TodoTargetEnum

    implicit val decoder: ScalarDecoder[TodoTargetEnum] = {
      case StringValue("COMMIT")       => Right(TodoTargetEnum.COMMIT)
      case StringValue("ISSUE")        => Right(TodoTargetEnum.ISSUE)
      case StringValue("MERGEREQUEST") => Right(TodoTargetEnum.MERGEREQUEST)
      case StringValue("DESIGN")       => Right(TodoTargetEnum.DESIGN)
      case StringValue("ALERT")        => Right(TodoTargetEnum.ALERT)
      case StringValue("EPIC")         => Right(TodoTargetEnum.EPIC)
      case other                       => Left(DecodingError(s"Can't build TodoTargetEnum from input $other"))
    }

    implicit val encoder: ArgEncoder[TodoTargetEnum] = new ArgEncoder[TodoTargetEnum] {

      override def encode(value: TodoTargetEnum): Value =
        value match {
          case TodoTargetEnum.COMMIT       => EnumValue("COMMIT")
          case TodoTargetEnum.ISSUE        => EnumValue("ISSUE")
          case TodoTargetEnum.MERGEREQUEST => EnumValue("MERGEREQUEST")
          case TodoTargetEnum.DESIGN       => EnumValue("DESIGN")
          case TodoTargetEnum.ALERT        => EnumValue("ALERT")
          case TodoTargetEnum.EPIC         => EnumValue("EPIC")
        }

      override def typeName: String = "TodoTargetEnum"
    }

  }

  sealed trait TypeEnum extends scala.Product with scala.Serializable

  object TypeEnum {
    case object personal extends TypeEnum
    case object project extends TypeEnum

    implicit val decoder: ScalarDecoder[TypeEnum] = {
      case StringValue("personal") => Right(TypeEnum.personal)
      case StringValue("project")  => Right(TypeEnum.project)
      case other                   => Left(DecodingError(s"Can't build TypeEnum from input $other"))
    }

    implicit val encoder: ArgEncoder[TypeEnum] = new ArgEncoder[TypeEnum] {

      override def encode(value: TypeEnum): Value =
        value match {
          case TypeEnum.personal => EnumValue("personal")
          case TypeEnum.project  => EnumValue("project")
        }

      override def typeName: String = "TypeEnum"
    }

  }

  sealed trait UserState extends scala.Product with scala.Serializable

  object UserState {
    case object active extends UserState
    case object blocked extends UserState
    case object deactivated extends UserState

    implicit val decoder: ScalarDecoder[UserState] = {
      case StringValue("active")      => Right(UserState.active)
      case StringValue("blocked")     => Right(UserState.blocked)
      case StringValue("deactivated") => Right(UserState.deactivated)
      case other                      => Left(DecodingError(s"Can't build UserState from input $other"))
    }

    implicit val encoder: ArgEncoder[UserState] = new ArgEncoder[UserState] {

      override def encode(value: UserState): Value =
        value match {
          case UserState.active      => EnumValue("active")
          case UserState.blocked     => EnumValue("blocked")
          case UserState.deactivated => EnumValue("deactivated")
        }

      override def typeName: String = "UserState"
    }

  }

  sealed trait VisibilityLevelsEnum extends scala.Product with scala.Serializable

  object VisibilityLevelsEnum {
    case object `private` extends VisibilityLevelsEnum
    case object internal extends VisibilityLevelsEnum
    case object public extends VisibilityLevelsEnum

    implicit val decoder: ScalarDecoder[VisibilityLevelsEnum] = {
      case StringValue("private")  => Right(VisibilityLevelsEnum.`private`)
      case StringValue("internal") => Right(VisibilityLevelsEnum.internal)
      case StringValue("public")   => Right(VisibilityLevelsEnum.public)
      case other                   => Left(DecodingError(s"Can't build VisibilityLevelsEnum from input $other"))
    }

    implicit val encoder: ArgEncoder[VisibilityLevelsEnum] = new ArgEncoder[VisibilityLevelsEnum] {

      override def encode(value: VisibilityLevelsEnum): Value =
        value match {
          case VisibilityLevelsEnum.`private` => EnumValue("private")
          case VisibilityLevelsEnum.internal  => EnumValue("internal")
          case VisibilityLevelsEnum.public    => EnumValue("public")
        }

      override def typeName: String = "VisibilityLevelsEnum"
    }

  }

  sealed trait VisibilityScopesEnum extends scala.Product with scala.Serializable

  object VisibilityScopesEnum {
    case object `private` extends VisibilityScopesEnum
    case object internal extends VisibilityScopesEnum
    case object public extends VisibilityScopesEnum

    implicit val decoder: ScalarDecoder[VisibilityScopesEnum] = {
      case StringValue("private")  => Right(VisibilityScopesEnum.`private`)
      case StringValue("internal") => Right(VisibilityScopesEnum.internal)
      case StringValue("public")   => Right(VisibilityScopesEnum.public)
      case other                   => Left(DecodingError(s"Can't build VisibilityScopesEnum from input $other"))
    }

    implicit val encoder: ArgEncoder[VisibilityScopesEnum] = new ArgEncoder[VisibilityScopesEnum] {

      override def encode(value: VisibilityScopesEnum): Value =
        value match {
          case VisibilityScopesEnum.`private` => EnumValue("private")
          case VisibilityScopesEnum.internal  => EnumValue("internal")
          case VisibilityScopesEnum.public    => EnumValue("public")
        }

      override def typeName: String = "VisibilityScopesEnum"
    }

  }

  sealed trait VulnerabilityIssueLinkType extends scala.Product with scala.Serializable

  object VulnerabilityIssueLinkType {
    case object RELATED extends VulnerabilityIssueLinkType
    case object CREATED extends VulnerabilityIssueLinkType

    implicit val decoder: ScalarDecoder[VulnerabilityIssueLinkType] = {
      case StringValue("RELATED") => Right(VulnerabilityIssueLinkType.RELATED)
      case StringValue("CREATED") => Right(VulnerabilityIssueLinkType.CREATED)
      case other                  => Left(DecodingError(s"Can't build VulnerabilityIssueLinkType from input $other"))
    }

    implicit val encoder: ArgEncoder[VulnerabilityIssueLinkType] = new ArgEncoder[VulnerabilityIssueLinkType] {

      override def encode(value: VulnerabilityIssueLinkType): Value =
        value match {
          case VulnerabilityIssueLinkType.RELATED => EnumValue("RELATED")
          case VulnerabilityIssueLinkType.CREATED => EnumValue("CREATED")
        }

      override def typeName: String = "VulnerabilityIssueLinkType"
    }

  }

  sealed trait VulnerabilityReportType extends scala.Product with scala.Serializable

  object VulnerabilityReportType {
    case object SAST extends VulnerabilityReportType
    case object DEPENDENCY_SCANNING extends VulnerabilityReportType
    case object CONTAINER_SCANNING extends VulnerabilityReportType
    case object DAST extends VulnerabilityReportType
    case object SECRET_DETECTION extends VulnerabilityReportType
    case object COVERAGE_FUZZING extends VulnerabilityReportType

    implicit val decoder: ScalarDecoder[VulnerabilityReportType] = {
      case StringValue("SAST")                => Right(VulnerabilityReportType.SAST)
      case StringValue("DEPENDENCY_SCANNING") => Right(VulnerabilityReportType.DEPENDENCY_SCANNING)
      case StringValue("CONTAINER_SCANNING")  => Right(VulnerabilityReportType.CONTAINER_SCANNING)
      case StringValue("DAST")                => Right(VulnerabilityReportType.DAST)
      case StringValue("SECRET_DETECTION")    => Right(VulnerabilityReportType.SECRET_DETECTION)
      case StringValue("COVERAGE_FUZZING")    => Right(VulnerabilityReportType.COVERAGE_FUZZING)
      case other                              => Left(DecodingError(s"Can't build VulnerabilityReportType from input $other"))
    }

    implicit val encoder: ArgEncoder[VulnerabilityReportType] = new ArgEncoder[VulnerabilityReportType] {

      override def encode(value: VulnerabilityReportType): Value =
        value match {
          case VulnerabilityReportType.SAST                => EnumValue("SAST")
          case VulnerabilityReportType.DEPENDENCY_SCANNING => EnumValue("DEPENDENCY_SCANNING")
          case VulnerabilityReportType.CONTAINER_SCANNING  => EnumValue("CONTAINER_SCANNING")
          case VulnerabilityReportType.DAST                => EnumValue("DAST")
          case VulnerabilityReportType.SECRET_DETECTION    => EnumValue("SECRET_DETECTION")
          case VulnerabilityReportType.COVERAGE_FUZZING    => EnumValue("COVERAGE_FUZZING")
        }

      override def typeName: String = "VulnerabilityReportType"
    }

  }

  sealed trait VulnerabilitySeverity extends scala.Product with scala.Serializable

  object VulnerabilitySeverity {
    case object INFO extends VulnerabilitySeverity
    case object UNKNOWN extends VulnerabilitySeverity
    case object LOW extends VulnerabilitySeverity
    case object MEDIUM extends VulnerabilitySeverity
    case object HIGH extends VulnerabilitySeverity
    case object CRITICAL extends VulnerabilitySeverity

    implicit val decoder: ScalarDecoder[VulnerabilitySeverity] = {
      case StringValue("INFO")     => Right(VulnerabilitySeverity.INFO)
      case StringValue("UNKNOWN")  => Right(VulnerabilitySeverity.UNKNOWN)
      case StringValue("LOW")      => Right(VulnerabilitySeverity.LOW)
      case StringValue("MEDIUM")   => Right(VulnerabilitySeverity.MEDIUM)
      case StringValue("HIGH")     => Right(VulnerabilitySeverity.HIGH)
      case StringValue("CRITICAL") => Right(VulnerabilitySeverity.CRITICAL)
      case other                   => Left(DecodingError(s"Can't build VulnerabilitySeverity from input $other"))
    }

    implicit val encoder: ArgEncoder[VulnerabilitySeverity] = new ArgEncoder[VulnerabilitySeverity] {

      override def encode(value: VulnerabilitySeverity): Value =
        value match {
          case VulnerabilitySeverity.INFO     => EnumValue("INFO")
          case VulnerabilitySeverity.UNKNOWN  => EnumValue("UNKNOWN")
          case VulnerabilitySeverity.LOW      => EnumValue("LOW")
          case VulnerabilitySeverity.MEDIUM   => EnumValue("MEDIUM")
          case VulnerabilitySeverity.HIGH     => EnumValue("HIGH")
          case VulnerabilitySeverity.CRITICAL => EnumValue("CRITICAL")
        }

      override def typeName: String = "VulnerabilitySeverity"
    }

  }

  sealed trait VulnerabilityState extends scala.Product with scala.Serializable

  object VulnerabilityState {
    case object DETECTED extends VulnerabilityState
    case object DISMISSED extends VulnerabilityState
    case object RESOLVED extends VulnerabilityState
    case object CONFIRMED extends VulnerabilityState

    implicit val decoder: ScalarDecoder[VulnerabilityState] = {
      case StringValue("DETECTED")  => Right(VulnerabilityState.DETECTED)
      case StringValue("DISMISSED") => Right(VulnerabilityState.DISMISSED)
      case StringValue("RESOLVED")  => Right(VulnerabilityState.RESOLVED)
      case StringValue("CONFIRMED") => Right(VulnerabilityState.CONFIRMED)
      case other                    => Left(DecodingError(s"Can't build VulnerabilityState from input $other"))
    }

    implicit val encoder: ArgEncoder[VulnerabilityState] = new ArgEncoder[VulnerabilityState] {

      override def encode(value: VulnerabilityState): Value =
        value match {
          case VulnerabilityState.DETECTED  => EnumValue("DETECTED")
          case VulnerabilityState.DISMISSED => EnumValue("DISMISSED")
          case VulnerabilityState.RESOLVED  => EnumValue("RESOLVED")
          case VulnerabilityState.CONFIRMED => EnumValue("CONFIRMED")
        }

      override def typeName: String = "VulnerabilityState"
    }

  }

  type AccessLevel

  object AccessLevel {

    /**
      * Integer representation of access level
      */
    def integerValue: SelectionBuilder[AccessLevel, Option[Int]] = Field("integerValue", OptionOf(Scalar()))

    /**
      * String representation of access level
      */
    def stringValue: SelectionBuilder[AccessLevel, Option[AccessLevelEnum]] = Field("stringValue", OptionOf(Scalar()))
  }

  type AddAwardEmojiPayload

  object AddAwardEmojiPayload {

    /**
      * The award emoji after mutation
      */
    def awardEmoji[A](innerSelection: SelectionBuilder[AwardEmoji, A]): SelectionBuilder[AddAwardEmojiPayload, Option[A]] =
      Field("awardEmoji", OptionOf(Obj(innerSelection)))

    /**
      * A unique identifier for the client performing the mutation.
      */
    def clientMutationId: SelectionBuilder[AddAwardEmojiPayload, Option[String]] = Field("clientMutationId", OptionOf(Scalar()))

    /**
      * Errors encountered during execution of the mutation.
      */
    def errors: SelectionBuilder[AddAwardEmojiPayload, List[String]] = Field("errors", ListOf(Scalar()))
  }

  type AddProjectToSecurityDashboardPayload

  object AddProjectToSecurityDashboardPayload {

    /**
      * A unique identifier for the client performing the mutation.
      */
    def clientMutationId: SelectionBuilder[AddProjectToSecurityDashboardPayload, Option[String]] =
      Field("clientMutationId", OptionOf(Scalar()))

    /**
      * Errors encountered during execution of the mutation.
      */
    def errors: SelectionBuilder[AddProjectToSecurityDashboardPayload, List[String]] = Field("errors", ListOf(Scalar()))

    /**
      * Project that was added to the Instance Security Dashboard
      */
    def project[A](innerSelection: SelectionBuilder[Project, A]): SelectionBuilder[AddProjectToSecurityDashboardPayload, Option[A]] =
      Field("project", OptionOf(Obj(innerSelection)))
  }

  type AdminSidekiqQueuesDeleteJobsPayload

  object AdminSidekiqQueuesDeleteJobsPayload {

    /**
      * A unique identifier for the client performing the mutation.
      */
    def clientMutationId: SelectionBuilder[AdminSidekiqQueuesDeleteJobsPayload, Option[String]] =
      Field("clientMutationId", OptionOf(Scalar()))

    /**
      * Errors encountered during execution of the mutation.
      */
    def errors: SelectionBuilder[AdminSidekiqQueuesDeleteJobsPayload, List[String]] = Field("errors", ListOf(Scalar()))

    /**
      * Information about the status of the deletion request
      */
    def result[A](
      innerSelection: SelectionBuilder[DeleteJobsResponse, A]
    ): SelectionBuilder[AdminSidekiqQueuesDeleteJobsPayload, Option[A]] = Field("result", OptionOf(Obj(innerSelection)))

  }

  type AlertManagementAlert

  object AlertManagementAlert {

    /**
      * Assignees of the alert
      */
    def assignees[A](
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[UserConnection, A]
    ): SelectionBuilder[AlertManagementAlert, Option[A]] =
      Field(
        "assignees",
        OptionOf(Obj(innerSelection)),
        arguments = List(Argument("after", after), Argument("before", before), Argument("first", first), Argument("last", last))
      )

    /**
      * Timestamp the alert was created
      */
    def createdAt: SelectionBuilder[AlertManagementAlert, Option[Time]] = Field("createdAt", OptionOf(Scalar()))

    /**
      * Description of the alert
      */
    def description: SelectionBuilder[AlertManagementAlert, Option[String]] = Field("description", OptionOf(Scalar()))

    /**
      * Alert details
      */
    def details: SelectionBuilder[AlertManagementAlert, Option[JSON]] = Field("details", OptionOf(Scalar()))

    /**
      * All discussions on this noteable
      */
    def discussions[A](
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[DiscussionConnection, A]
    ): SelectionBuilder[AlertManagementAlert, A] =
      Field(
        "discussions",
        Obj(innerSelection),
        arguments = List(Argument("after", after), Argument("before", before), Argument("first", first), Argument("last", last))
      )

    /**
      * Timestamp the alert ended
      */
    def endedAt: SelectionBuilder[AlertManagementAlert, Option[Time]] = Field("endedAt", OptionOf(Scalar()))

    /**
      * Number of events of this alert
      */
    def eventCount: SelectionBuilder[AlertManagementAlert, Option[Int]] = Field("eventCount", OptionOf(Scalar()))

    /**
      * List of hosts the alert came from
      */
    def hosts: SelectionBuilder[AlertManagementAlert, Option[List[String]]] = Field("hosts", OptionOf(ListOf(Scalar())))

    /**
      * Internal ID of the alert
      */
    def iid: SelectionBuilder[AlertManagementAlert, String] = Field("iid", Scalar())

    /**
      * Internal ID of the GitLab issue attached to the alert
      */
    def issueIid: SelectionBuilder[AlertManagementAlert, Option[String]] = Field("issueIid", OptionOf(Scalar()))

    /**
      * URL for metrics embed for the alert
      */
    def metricsDashboardUrl: SelectionBuilder[AlertManagementAlert, Option[String]] = Field("metricsDashboardUrl", OptionOf(Scalar()))

    /**
      * Monitoring tool the alert came from
      */
    def monitoringTool: SelectionBuilder[AlertManagementAlert, Option[String]] = Field("monitoringTool", OptionOf(Scalar()))

    /**
      * All notes on this noteable
      */
    def notes[A](
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[NoteConnection, A]
    ): SelectionBuilder[AlertManagementAlert, A] =
      Field(
        "notes",
        Obj(innerSelection),
        arguments = List(Argument("after", after), Argument("before", before), Argument("first", first), Argument("last", last))
      )

    /**
      * Service the alert came from
      */
    def service: SelectionBuilder[AlertManagementAlert, Option[String]] = Field("service", OptionOf(Scalar()))

    /**
      * Severity of the alert
      */
    def severity: SelectionBuilder[AlertManagementAlert, Option[AlertManagementSeverity]] = Field("severity", OptionOf(Scalar()))

    /**
      * Timestamp the alert was raised
      */
    def startedAt: SelectionBuilder[AlertManagementAlert, Option[Time]] = Field("startedAt", OptionOf(Scalar()))

    /**
      * Status of the alert
      */
    def status: SelectionBuilder[AlertManagementAlert, Option[AlertManagementStatus]] = Field("status", OptionOf(Scalar()))

    /**
      * Title of the alert
      */
    def title: SelectionBuilder[AlertManagementAlert, Option[String]] = Field("title", OptionOf(Scalar()))

    /**
      * Timestamp the alert was last updated
      */
    def updatedAt: SelectionBuilder[AlertManagementAlert, Option[Time]] = Field("updatedAt", OptionOf(Scalar()))
  }

  type AlertManagementAlertConnection

  object AlertManagementAlertConnection {

    /**
      * A list of edges.
      */
    def edges[A](
      innerSelection: SelectionBuilder[AlertManagementAlertEdge, A]
    ): SelectionBuilder[AlertManagementAlertConnection, Option[List[Option[A]]]] =
      Field("edges", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * A list of nodes.
      */
    def nodes[A](
      innerSelection: SelectionBuilder[AlertManagementAlert, A]
    ): SelectionBuilder[AlertManagementAlertConnection, Option[List[Option[A]]]] =
      Field("nodes", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * Information to aid in pagination.
      */
    def pageInfo[A](innerSelection: SelectionBuilder[PageInfo, A]): SelectionBuilder[AlertManagementAlertConnection, A] =
      Field("pageInfo", Obj(innerSelection))
  }

  type AlertManagementAlertEdge

  object AlertManagementAlertEdge {

    /**
      * A cursor for use in pagination.
      */
    def cursor: SelectionBuilder[AlertManagementAlertEdge, String] = Field("cursor", Scalar())

    /**
      * The item at the end of the edge.
      */
    def node[A](innerSelection: SelectionBuilder[AlertManagementAlert, A]): SelectionBuilder[AlertManagementAlertEdge, Option[A]] =
      Field("node", OptionOf(Obj(innerSelection)))
  }

  type AlertManagementAlertStatusCountsType

  object AlertManagementAlertStatusCountsType {

    /**
      * Number of alerts with status ACKNOWLEDGED for the project
      */
    def acknowledged: SelectionBuilder[AlertManagementAlertStatusCountsType, Option[Int]] = Field("acknowledged", OptionOf(Scalar()))

    /**
      * Total number of alerts for the project
      */
    def all: SelectionBuilder[AlertManagementAlertStatusCountsType, Option[Int]] = Field("all", OptionOf(Scalar()))

    /**
      * Number of alerts with status IGNORED for the project
      */
    def ignored: SelectionBuilder[AlertManagementAlertStatusCountsType, Option[Int]] = Field("ignored", OptionOf(Scalar()))

    /**
      * Number of alerts with status TRIGGERED or ACKNOWLEDGED for the project
      */
    def open: SelectionBuilder[AlertManagementAlertStatusCountsType, Option[Int]] = Field("open", OptionOf(Scalar()))

    /**
      * Number of alerts with status RESOLVED for the project
      */
    def resolved: SelectionBuilder[AlertManagementAlertStatusCountsType, Option[Int]] = Field("resolved", OptionOf(Scalar()))

    /**
      * Number of alerts with status TRIGGERED for the project
      */
    def triggered: SelectionBuilder[AlertManagementAlertStatusCountsType, Option[Int]] = Field("triggered", OptionOf(Scalar()))
  }

  type AlertSetAssigneesPayload

  object AlertSetAssigneesPayload {

    /**
      * The alert after mutation
      */
    def alert[A](innerSelection: SelectionBuilder[AlertManagementAlert, A]): SelectionBuilder[AlertSetAssigneesPayload, Option[A]] =
      Field("alert", OptionOf(Obj(innerSelection)))

    /**
      * A unique identifier for the client performing the mutation.
      */
    def clientMutationId: SelectionBuilder[AlertSetAssigneesPayload, Option[String]] = Field("clientMutationId", OptionOf(Scalar()))

    /**
      * Errors encountered during execution of the mutation.
      */
    def errors: SelectionBuilder[AlertSetAssigneesPayload, List[String]] = Field("errors", ListOf(Scalar()))

    /**
      * The issue created after mutation
      */
    def issue[A](innerSelection: SelectionBuilder[Issue, A]): SelectionBuilder[AlertSetAssigneesPayload, Option[A]] =
      Field("issue", OptionOf(Obj(innerSelection)))

    /**
      * The todo after mutation
      */
    def todo[A](innerSelection: SelectionBuilder[Todo, A]): SelectionBuilder[AlertSetAssigneesPayload, Option[A]] =
      Field("todo", OptionOf(Obj(innerSelection)))
  }

  type AlertTodoCreatePayload

  object AlertTodoCreatePayload {

    /**
      * The alert after mutation
      */
    def alert[A](innerSelection: SelectionBuilder[AlertManagementAlert, A]): SelectionBuilder[AlertTodoCreatePayload, Option[A]] =
      Field("alert", OptionOf(Obj(innerSelection)))

    /**
      * A unique identifier for the client performing the mutation.
      */
    def clientMutationId: SelectionBuilder[AlertTodoCreatePayload, Option[String]] = Field("clientMutationId", OptionOf(Scalar()))

    /**
      * Errors encountered during execution of the mutation.
      */
    def errors: SelectionBuilder[AlertTodoCreatePayload, List[String]] = Field("errors", ListOf(Scalar()))

    /**
      * The issue created after mutation
      */
    def issue[A](innerSelection: SelectionBuilder[Issue, A]): SelectionBuilder[AlertTodoCreatePayload, Option[A]] =
      Field("issue", OptionOf(Obj(innerSelection)))

    /**
      * The todo after mutation
      */
    def todo[A](innerSelection: SelectionBuilder[Todo, A]): SelectionBuilder[AlertTodoCreatePayload, Option[A]] =
      Field("todo", OptionOf(Obj(innerSelection)))
  }

  type AwardEmoji

  object AwardEmoji {

    /**
      * The emoji description
      */
    def description: SelectionBuilder[AwardEmoji, String] = Field("description", Scalar())

    /**
      * The emoji as an icon
      */
    def emoji: SelectionBuilder[AwardEmoji, String] = Field("emoji", Scalar())

    /**
      * The emoji name
      */
    def name: SelectionBuilder[AwardEmoji, String] = Field("name", Scalar())

    /**
      * The emoji in unicode
      */
    def unicode: SelectionBuilder[AwardEmoji, String] = Field("unicode", Scalar())

    /**
      * The unicode version for this emoji
      */
    def unicodeVersion: SelectionBuilder[AwardEmoji, String] = Field("unicodeVersion", Scalar())

    /**
      * The user who awarded the emoji
      */
    def user[A](innerSelection: SelectionBuilder[User, A]): SelectionBuilder[AwardEmoji, A] = Field("user", Obj(innerSelection))
  }

  type AwardEmojiAddPayload

  object AwardEmojiAddPayload {

    /**
      * The award emoji after mutation
      */
    def awardEmoji[A](innerSelection: SelectionBuilder[AwardEmoji, A]): SelectionBuilder[AwardEmojiAddPayload, Option[A]] =
      Field("awardEmoji", OptionOf(Obj(innerSelection)))

    /**
      * A unique identifier for the client performing the mutation.
      */
    def clientMutationId: SelectionBuilder[AwardEmojiAddPayload, Option[String]] = Field("clientMutationId", OptionOf(Scalar()))

    /**
      * Errors encountered during execution of the mutation.
      */
    def errors: SelectionBuilder[AwardEmojiAddPayload, List[String]] = Field("errors", ListOf(Scalar()))
  }

  type AwardEmojiRemovePayload

  object AwardEmojiRemovePayload {

    /**
      * The award emoji after mutation
      */
    def awardEmoji[A](innerSelection: SelectionBuilder[AwardEmoji, A]): SelectionBuilder[AwardEmojiRemovePayload, Option[A]] =
      Field("awardEmoji", OptionOf(Obj(innerSelection)))

    /**
      * A unique identifier for the client performing the mutation.
      */
    def clientMutationId: SelectionBuilder[AwardEmojiRemovePayload, Option[String]] = Field("clientMutationId", OptionOf(Scalar()))

    /**
      * Errors encountered during execution of the mutation.
      */
    def errors: SelectionBuilder[AwardEmojiRemovePayload, List[String]] = Field("errors", ListOf(Scalar()))
  }

  type AwardEmojiTogglePayload

  object AwardEmojiTogglePayload {

    /**
      * The award emoji after mutation
      */
    def awardEmoji[A](innerSelection: SelectionBuilder[AwardEmoji, A]): SelectionBuilder[AwardEmojiTogglePayload, Option[A]] =
      Field("awardEmoji", OptionOf(Obj(innerSelection)))

    /**
      * A unique identifier for the client performing the mutation.
      */
    def clientMutationId: SelectionBuilder[AwardEmojiTogglePayload, Option[String]] = Field("clientMutationId", OptionOf(Scalar()))

    /**
      * Errors encountered during execution of the mutation.
      */
    def errors: SelectionBuilder[AwardEmojiTogglePayload, List[String]] = Field("errors", ListOf(Scalar()))

    /**
      * Indicates the status of the emoji. True if the toggle awarded the emoji, and false if the toggle removed the emoji.
      */
    def toggledOn: SelectionBuilder[AwardEmojiTogglePayload, Boolean] = Field("toggledOn", Scalar())
  }

  type BaseService

  object BaseService {

    /**
      * Indicates if the service is active
      */
    def active: SelectionBuilder[BaseService, Option[Boolean]] = Field("active", OptionOf(Scalar()))

    /**
      * Class name of the service
      */
    def `type`: SelectionBuilder[BaseService, Option[String]] = Field("type", OptionOf(Scalar()))
  }

  type Blob

  object Blob {

    /**
      * Flat path of the entry
      */
    def flatPath: SelectionBuilder[Blob, String] = Field("flatPath", Scalar())

    /**
      * ID of the entry
      */
    def id: SelectionBuilder[Blob, String] = Field("id", Scalar())

    /**
      * LFS ID of the blob
      */
    def lfsOid: SelectionBuilder[Blob, Option[String]] = Field("lfsOid", OptionOf(Scalar()))

    /**
      * Blob mode in numeric format
      */
    def mode: SelectionBuilder[Blob, Option[String]] = Field("mode", OptionOf(Scalar()))

    /**
      * Name of the entry
      */
    def name: SelectionBuilder[Blob, String] = Field("name", Scalar())

    /**
      * Path of the entry
      */
    def path: SelectionBuilder[Blob, String] = Field("path", Scalar())

    /**
      * Last commit sha for the entry
      */
    def sha: SelectionBuilder[Blob, String] = Field("sha", Scalar())

    /**
      * Type of tree entry
      */
    def `type`: SelectionBuilder[Blob, EntryType] = Field("type", Scalar())

    /**
      * Web path of the blob
      */
    def webPath: SelectionBuilder[Blob, Option[String]] = Field("webPath", OptionOf(Scalar()))

    /**
      * Web URL of the blob
      */
    def webUrl: SelectionBuilder[Blob, Option[String]] = Field("webUrl", OptionOf(Scalar()))
  }

  type BlobConnection

  object BlobConnection {

    /**
      * A list of edges.
      */
    def edges[A](innerSelection: SelectionBuilder[BlobEdge, A]): SelectionBuilder[BlobConnection, Option[List[Option[A]]]] =
      Field("edges", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * A list of nodes.
      */
    def nodes[A](innerSelection: SelectionBuilder[Blob, A]): SelectionBuilder[BlobConnection, Option[List[Option[A]]]] =
      Field("nodes", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * Information to aid in pagination.
      */
    def pageInfo[A](innerSelection: SelectionBuilder[PageInfo, A]): SelectionBuilder[BlobConnection, A] =
      Field("pageInfo", Obj(innerSelection))
  }

  type BlobEdge

  object BlobEdge {

    /**
      * A cursor for use in pagination.
      */
    def cursor: SelectionBuilder[BlobEdge, String] = Field("cursor", Scalar())

    /**
      * The item at the end of the edge.
      */
    def node[A](innerSelection: SelectionBuilder[Blob, A]): SelectionBuilder[BlobEdge, Option[A]] =
      Field("node", OptionOf(Obj(innerSelection)))
  }

  type Board

  object Board {

    /**
      * ID (global ID) of the board
      */
    def id: SelectionBuilder[Board, String] = Field("id", Scalar())

    /**
      * Lists of the board
      */
    def lists[A](
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[BoardListConnection, A]
    ): SelectionBuilder[Board, Option[A]] =
      Field(
        "lists",
        OptionOf(Obj(innerSelection)),
        arguments = List(Argument("after", after), Argument("before", before), Argument("first", first), Argument("last", last))
      )

    /**
      * Name of the board
      */
    def name: SelectionBuilder[Board, Option[String]] = Field("name", OptionOf(Scalar()))

    /**
      * Weight of the board
      */
    def weight: SelectionBuilder[Board, Option[Int]] = Field("weight", OptionOf(Scalar()))
  }

  type BoardConnection

  object BoardConnection {

    /**
      * A list of edges.
      */
    def edges[A](innerSelection: SelectionBuilder[BoardEdge, A]): SelectionBuilder[BoardConnection, Option[List[Option[A]]]] =
      Field("edges", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * A list of nodes.
      */
    def nodes[A](innerSelection: SelectionBuilder[Board, A]): SelectionBuilder[BoardConnection, Option[List[Option[A]]]] =
      Field("nodes", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * Information to aid in pagination.
      */
    def pageInfo[A](innerSelection: SelectionBuilder[PageInfo, A]): SelectionBuilder[BoardConnection, A] =
      Field("pageInfo", Obj(innerSelection))
  }

  type BoardEdge

  object BoardEdge {

    /**
      * A cursor for use in pagination.
      */
    def cursor: SelectionBuilder[BoardEdge, String] = Field("cursor", Scalar())

    /**
      * The item at the end of the edge.
      */
    def node[A](innerSelection: SelectionBuilder[Board, A]): SelectionBuilder[BoardEdge, Option[A]] =
      Field("node", OptionOf(Obj(innerSelection)))
  }

  type BoardList

  object BoardList {

    /**
      * Assignee in the list
      */
    def assignee[A](innerSelection: SelectionBuilder[User, A]): SelectionBuilder[BoardList, Option[A]] =
      Field("assignee", OptionOf(Obj(innerSelection)))

    /**
      * Indicates if list is collapsed for this user
      */
    def collapsed: SelectionBuilder[BoardList, Option[Boolean]] = Field("collapsed", OptionOf(Scalar()))

    /**
      * ID (global ID) of the list
      */
    def id: SelectionBuilder[BoardList, String] = Field("id", Scalar())

    /**
      * Label of the list
      */
    def label[A](innerSelection: SelectionBuilder[Label, A]): SelectionBuilder[BoardList, Option[A]] =
      Field("label", OptionOf(Obj(innerSelection)))

    /**
      * The current limit metric for the list
      */
    def limitMetric: SelectionBuilder[BoardList, Option[ListLimitMetric]] = Field("limitMetric", OptionOf(Scalar()))

    /**
      * Type of the list
      */
    def listType: SelectionBuilder[BoardList, String] = Field("listType", Scalar())

    /**
      * Maximum number of issues in the list
      */
    def maxIssueCount: SelectionBuilder[BoardList, Option[Int]] = Field("maxIssueCount", OptionOf(Scalar()))

    /**
      * Maximum weight of issues in the list
      */
    def maxIssueWeight: SelectionBuilder[BoardList, Option[Int]] = Field("maxIssueWeight", OptionOf(Scalar()))

    /**
      * Milestone of the list
      */
    def milestone[A](innerSelection: SelectionBuilder[Milestone, A]): SelectionBuilder[BoardList, Option[A]] =
      Field("milestone", OptionOf(Obj(innerSelection)))

    /**
      * Position of list within the board
      */
    def position: SelectionBuilder[BoardList, Option[Int]] = Field("position", OptionOf(Scalar()))

    /**
      * Title of the list
      */
    def title: SelectionBuilder[BoardList, String] = Field("title", Scalar())
  }

  type BoardListConnection

  object BoardListConnection {

    /**
      * A list of edges.
      */
    def edges[A](innerSelection: SelectionBuilder[BoardListEdge, A]): SelectionBuilder[BoardListConnection, Option[List[Option[A]]]] =
      Field("edges", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * A list of nodes.
      */
    def nodes[A](innerSelection: SelectionBuilder[BoardList, A]): SelectionBuilder[BoardListConnection, Option[List[Option[A]]]] =
      Field("nodes", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * Information to aid in pagination.
      */
    def pageInfo[A](innerSelection: SelectionBuilder[PageInfo, A]): SelectionBuilder[BoardListConnection, A] =
      Field("pageInfo", Obj(innerSelection))
  }

  type BoardListEdge

  object BoardListEdge {

    /**
      * A cursor for use in pagination.
      */
    def cursor: SelectionBuilder[BoardListEdge, String] = Field("cursor", Scalar())

    /**
      * The item at the end of the edge.
      */
    def node[A](innerSelection: SelectionBuilder[BoardList, A]): SelectionBuilder[BoardListEdge, Option[A]] =
      Field("node", OptionOf(Obj(innerSelection)))
  }

  type BoardListUpdateLimitMetricsPayload

  object BoardListUpdateLimitMetricsPayload {

    /**
      * A unique identifier for the client performing the mutation.
      */
    def clientMutationId: SelectionBuilder[BoardListUpdateLimitMetricsPayload, Option[String]] =
      Field("clientMutationId", OptionOf(Scalar()))

    /**
      * Errors encountered during execution of the mutation.
      */
    def errors: SelectionBuilder[BoardListUpdateLimitMetricsPayload, List[String]] = Field("errors", ListOf(Scalar()))

    /**
      * The updated list
      */
    def list[A](innerSelection: SelectionBuilder[BoardList, A]): SelectionBuilder[BoardListUpdateLimitMetricsPayload, Option[A]] =
      Field("list", OptionOf(Obj(innerSelection)))
  }

  type Branch

  object Branch {

    /**
      * Commit for the branch
      */
    def commit[A](innerSelection: SelectionBuilder[Commit, A]): SelectionBuilder[Branch, Option[A]] =
      Field("commit", OptionOf(Obj(innerSelection)))

    /**
      * Name of the branch
      */
    def name: SelectionBuilder[Branch, String] = Field("name", Scalar())
  }

  type Commit

  object Commit {

    /**
      * Author of the commit
      */
    def author[A](innerSelection: SelectionBuilder[User, A]): SelectionBuilder[Commit, Option[A]] =
      Field("author", OptionOf(Obj(innerSelection)))

    /**
      * Commit authors gravatar
      */
    def authorGravatar: SelectionBuilder[Commit, Option[String]] = Field("authorGravatar", OptionOf(Scalar()))

    /**
      * Commit authors name
      */
    def authorName: SelectionBuilder[Commit, Option[String]] = Field("authorName", OptionOf(Scalar()))

    /**
      * Timestamp of when the commit was authored
      */
    def authoredDate: SelectionBuilder[Commit, Option[Time]] = Field("authoredDate", OptionOf(Scalar()))

    /**
      * Description of the commit message
      */
    def description: SelectionBuilder[Commit, Option[String]] = Field("description", OptionOf(Scalar()))

    /**
      * The GitLab Flavored Markdown rendering of `description`
      */
    def descriptionHtml: SelectionBuilder[Commit, Option[String]] = Field("descriptionHtml", OptionOf(Scalar()))

    /**
      * ID (global ID) of the commit
      */
    def id: SelectionBuilder[Commit, String] = Field("id", Scalar())

    /**
      * Latest pipeline of the commit. Deprecated in 12.5: Use `pipelines`
      */
    @deprecated("Use `pipelines`. Deprecated in 12.5", "")
    def latestPipeline[A](
      status: Option[PipelineStatusEnum] = None,
      ref: Option[String] = None,
      sha: Option[String] = None
    )(
      innerSelection: SelectionBuilder[Pipeline, A]
    ): SelectionBuilder[Commit, Option[A]] =
      Field(
        "latestPipeline",
        OptionOf(Obj(innerSelection)),
        arguments = List(Argument("status", status), Argument("ref", ref), Argument("sha", sha))
      )

    /**
      * Raw commit message
      */
    def message: SelectionBuilder[Commit, Option[String]] = Field("message", OptionOf(Scalar()))

    /**
      * Pipelines of the commit ordered latest first
      */
    def pipelines[A](
      status: Option[PipelineStatusEnum] = None,
      ref: Option[String] = None,
      sha: Option[String] = None,
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[PipelineConnection, A]
    ): SelectionBuilder[Commit, Option[A]] =
      Field(
        "pipelines",
        OptionOf(Obj(innerSelection)),
        arguments = List(
          Argument("status", status),
          Argument("ref", ref),
          Argument("sha", sha),
          Argument("after", after),
          Argument("before", before),
          Argument("first", first),
          Argument("last", last)
        )
      )

    /**
      * SHA1 ID of the commit
      */
    def sha: SelectionBuilder[Commit, String] = Field("sha", Scalar())

    /**
      * Rendered HTML of the commit signature
      */
    def signatureHtml: SelectionBuilder[Commit, Option[String]] = Field("signatureHtml", OptionOf(Scalar()))

    /**
      * Title of the commit message
      */
    def title: SelectionBuilder[Commit, Option[String]] = Field("title", OptionOf(Scalar()))

    /**
      * The GitLab Flavored Markdown rendering of `title`
      */
    def titleHtml: SelectionBuilder[Commit, Option[String]] = Field("titleHtml", OptionOf(Scalar()))

    /**
      * Web path of the commit
      */
    def webPath: SelectionBuilder[Commit, String] = Field("webPath", Scalar())

    /**
      * Web URL of the commit
      */
    def webUrl: SelectionBuilder[Commit, String] = Field("webUrl", Scalar())
  }

  type CommitCreatePayload

  object CommitCreatePayload {

    /**
      * A unique identifier for the client performing the mutation.
      */
    def clientMutationId: SelectionBuilder[CommitCreatePayload, Option[String]] = Field("clientMutationId", OptionOf(Scalar()))

    /**
      * The commit after mutation
      */
    def commit[A](innerSelection: SelectionBuilder[Commit, A]): SelectionBuilder[CommitCreatePayload, Option[A]] =
      Field("commit", OptionOf(Obj(innerSelection)))

    /**
      * Errors encountered during execution of the mutation.
      */
    def errors: SelectionBuilder[CommitCreatePayload, List[String]] = Field("errors", ListOf(Scalar()))
  }

  type ComplianceFramework

  object ComplianceFramework {

    /**
      * Name of the compliance framework
      */
    def name: SelectionBuilder[ComplianceFramework, ProjectSettingEnum] = Field("name", Scalar())
  }

  type ComplianceFrameworkConnection

  object ComplianceFrameworkConnection {

    /**
      * A list of edges.
      */
    def edges[A](
      innerSelection: SelectionBuilder[ComplianceFrameworkEdge, A]
    ): SelectionBuilder[ComplianceFrameworkConnection, Option[List[Option[A]]]] =
      Field("edges", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * A list of nodes.
      */
    def nodes[A](
      innerSelection: SelectionBuilder[ComplianceFramework, A]
    ): SelectionBuilder[ComplianceFrameworkConnection, Option[List[Option[A]]]] =
      Field("nodes", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * Information to aid in pagination.
      */
    def pageInfo[A](innerSelection: SelectionBuilder[PageInfo, A]): SelectionBuilder[ComplianceFrameworkConnection, A] =
      Field("pageInfo", Obj(innerSelection))
  }

  type ComplianceFrameworkEdge

  object ComplianceFrameworkEdge {

    /**
      * A cursor for use in pagination.
      */
    def cursor: SelectionBuilder[ComplianceFrameworkEdge, String] = Field("cursor", Scalar())

    /**
      * The item at the end of the edge.
      */
    def node[A](innerSelection: SelectionBuilder[ComplianceFramework, A]): SelectionBuilder[ComplianceFrameworkEdge, Option[A]] =
      Field("node", OptionOf(Obj(innerSelection)))
  }

  type ContainerExpirationPolicy

  object ContainerExpirationPolicy {

    /**
      * This container expiration policy schedule
      */
    def cadence: SelectionBuilder[ContainerExpirationPolicy, ContainerExpirationPolicyCadenceEnum] = Field("cadence", Scalar())

    /**
      * Timestamp of when the container expiration policy was created
      */
    def createdAt: SelectionBuilder[ContainerExpirationPolicy, Time] = Field("createdAt", Scalar())

    /**
      * Indicates whether this container expiration policy is enabled
      */
    def enabled: SelectionBuilder[ContainerExpirationPolicy, Boolean] = Field("enabled", Scalar())

    /**
      * Number of tags to retain
      */
    def keepN: SelectionBuilder[ContainerExpirationPolicy, Option[ContainerExpirationPolicyKeepEnum]] = Field("keepN", OptionOf(Scalar()))

    /**
      * Tags with names matching this regex pattern will expire
      */
    def nameRegex: SelectionBuilder[ContainerExpirationPolicy, Option[UntrustedRegexp]] = Field("nameRegex", OptionOf(Scalar()))

    /**
      * Tags with names matching this regex pattern will be preserved
      */
    def nameRegexKeep: SelectionBuilder[ContainerExpirationPolicy, Option[UntrustedRegexp]] = Field("nameRegexKeep", OptionOf(Scalar()))

    /**
      * Next time that this container expiration policy will get executed
      */
    def nextRunAt: SelectionBuilder[ContainerExpirationPolicy, Option[Time]] = Field("nextRunAt", OptionOf(Scalar()))

    /**
      * Tags older that this will expire
      */
    def olderThan: SelectionBuilder[ContainerExpirationPolicy, Option[ContainerExpirationPolicyOlderThanEnum]] =
      Field("olderThan", OptionOf(Scalar()))

    /**
      * Timestamp of when the container expiration policy was updated
      */
    def updatedAt: SelectionBuilder[ContainerExpirationPolicy, Time] = Field("updatedAt", Scalar())
  }

  type CreateAlertIssuePayload

  object CreateAlertIssuePayload {

    /**
      * The alert after mutation
      */
    def alert[A](innerSelection: SelectionBuilder[AlertManagementAlert, A]): SelectionBuilder[CreateAlertIssuePayload, Option[A]] =
      Field("alert", OptionOf(Obj(innerSelection)))

    /**
      * A unique identifier for the client performing the mutation.
      */
    def clientMutationId: SelectionBuilder[CreateAlertIssuePayload, Option[String]] = Field("clientMutationId", OptionOf(Scalar()))

    /**
      * Errors encountered during execution of the mutation.
      */
    def errors: SelectionBuilder[CreateAlertIssuePayload, List[String]] = Field("errors", ListOf(Scalar()))

    /**
      * The issue created after mutation
      */
    def issue[A](innerSelection: SelectionBuilder[Issue, A]): SelectionBuilder[CreateAlertIssuePayload, Option[A]] =
      Field("issue", OptionOf(Obj(innerSelection)))

    /**
      * The todo after mutation
      */
    def todo[A](innerSelection: SelectionBuilder[Todo, A]): SelectionBuilder[CreateAlertIssuePayload, Option[A]] =
      Field("todo", OptionOf(Obj(innerSelection)))
  }

  type CreateAnnotationPayload

  object CreateAnnotationPayload {

    /**
      * The created annotation
      */
    def annotation[A](
      innerSelection: SelectionBuilder[MetricsDashboardAnnotation, A]
    ): SelectionBuilder[CreateAnnotationPayload, Option[A]] = Field("annotation", OptionOf(Obj(innerSelection)))

    /**
      * A unique identifier for the client performing the mutation.
      */
    def clientMutationId: SelectionBuilder[CreateAnnotationPayload, Option[String]] = Field("clientMutationId", OptionOf(Scalar()))

    /**
      * Errors encountered during execution of the mutation.
      */
    def errors: SelectionBuilder[CreateAnnotationPayload, List[String]] = Field("errors", ListOf(Scalar()))
  }

  type CreateBranchPayload

  object CreateBranchPayload {

    /**
      * Branch after mutation
      */
    def branch[A](innerSelection: SelectionBuilder[Branch, A]): SelectionBuilder[CreateBranchPayload, Option[A]] =
      Field("branch", OptionOf(Obj(innerSelection)))

    /**
      * A unique identifier for the client performing the mutation.
      */
    def clientMutationId: SelectionBuilder[CreateBranchPayload, Option[String]] = Field("clientMutationId", OptionOf(Scalar()))

    /**
      * Errors encountered during execution of the mutation.
      */
    def errors: SelectionBuilder[CreateBranchPayload, List[String]] = Field("errors", ListOf(Scalar()))
  }

  type CreateDiffNotePayload

  object CreateDiffNotePayload {

    /**
      * A unique identifier for the client performing the mutation.
      */
    def clientMutationId: SelectionBuilder[CreateDiffNotePayload, Option[String]] = Field("clientMutationId", OptionOf(Scalar()))

    /**
      * Errors encountered during execution of the mutation.
      */
    def errors: SelectionBuilder[CreateDiffNotePayload, List[String]] = Field("errors", ListOf(Scalar()))

    /**
      * The note after mutation
      */
    def note[A](innerSelection: SelectionBuilder[Note, A]): SelectionBuilder[CreateDiffNotePayload, Option[A]] =
      Field("note", OptionOf(Obj(innerSelection)))
  }

  type CreateEpicPayload

  object CreateEpicPayload {

    /**
      * A unique identifier for the client performing the mutation.
      */
    def clientMutationId: SelectionBuilder[CreateEpicPayload, Option[String]] = Field("clientMutationId", OptionOf(Scalar()))

    /**
      * The created epic
      */
    def epic[A](innerSelection: SelectionBuilder[Epic, A]): SelectionBuilder[CreateEpicPayload, Option[A]] =
      Field("epic", OptionOf(Obj(innerSelection)))

    /**
      * Errors encountered during execution of the mutation.
      */
    def errors: SelectionBuilder[CreateEpicPayload, List[String]] = Field("errors", ListOf(Scalar()))
  }

  type CreateImageDiffNotePayload

  object CreateImageDiffNotePayload {

    /**
      * A unique identifier for the client performing the mutation.
      */
    def clientMutationId: SelectionBuilder[CreateImageDiffNotePayload, Option[String]] = Field("clientMutationId", OptionOf(Scalar()))

    /**
      * Errors encountered during execution of the mutation.
      */
    def errors: SelectionBuilder[CreateImageDiffNotePayload, List[String]] = Field("errors", ListOf(Scalar()))

    /**
      * The note after mutation
      */
    def note[A](innerSelection: SelectionBuilder[Note, A]): SelectionBuilder[CreateImageDiffNotePayload, Option[A]] =
      Field("note", OptionOf(Obj(innerSelection)))
  }

  type CreateIterationPayload

  object CreateIterationPayload {

    /**
      * A unique identifier for the client performing the mutation.
      */
    def clientMutationId: SelectionBuilder[CreateIterationPayload, Option[String]] = Field("clientMutationId", OptionOf(Scalar()))

    /**
      * Errors encountered during execution of the mutation.
      */
    def errors: SelectionBuilder[CreateIterationPayload, List[String]] = Field("errors", ListOf(Scalar()))

    /**
      * The created iteration
      */
    def iteration[A](innerSelection: SelectionBuilder[Iteration, A]): SelectionBuilder[CreateIterationPayload, Option[A]] =
      Field("iteration", OptionOf(Obj(innerSelection)))
  }

  type CreateNotePayload

  object CreateNotePayload {

    /**
      * A unique identifier for the client performing the mutation.
      */
    def clientMutationId: SelectionBuilder[CreateNotePayload, Option[String]] = Field("clientMutationId", OptionOf(Scalar()))

    /**
      * Errors encountered during execution of the mutation.
      */
    def errors: SelectionBuilder[CreateNotePayload, List[String]] = Field("errors", ListOf(Scalar()))

    /**
      * The note after mutation
      */
    def note[A](innerSelection: SelectionBuilder[Note, A]): SelectionBuilder[CreateNotePayload, Option[A]] =
      Field("note", OptionOf(Obj(innerSelection)))
  }

  type CreateRequirementPayload

  object CreateRequirementPayload {

    /**
      * A unique identifier for the client performing the mutation.
      */
    def clientMutationId: SelectionBuilder[CreateRequirementPayload, Option[String]] = Field("clientMutationId", OptionOf(Scalar()))

    /**
      * Errors encountered during execution of the mutation.
      */
    def errors: SelectionBuilder[CreateRequirementPayload, List[String]] = Field("errors", ListOf(Scalar()))

    /**
      * The requirement after mutation
      */
    def requirement[A](innerSelection: SelectionBuilder[Requirement, A]): SelectionBuilder[CreateRequirementPayload, Option[A]] =
      Field("requirement", OptionOf(Obj(innerSelection)))
  }

  type CreateSnippetPayload

  object CreateSnippetPayload {

    /**
      * A unique identifier for the client performing the mutation.
      */
    def clientMutationId: SelectionBuilder[CreateSnippetPayload, Option[String]] = Field("clientMutationId", OptionOf(Scalar()))

    /**
      * Errors encountered during execution of the mutation.
      */
    def errors: SelectionBuilder[CreateSnippetPayload, List[String]] = Field("errors", ListOf(Scalar()))

    /**
      * The snippet after mutation
      */
    def snippet[A](innerSelection: SelectionBuilder[Snippet, A]): SelectionBuilder[CreateSnippetPayload, Option[A]] =
      Field("snippet", OptionOf(Obj(innerSelection)))
  }

  type DastSiteProfileCreatePayload

  object DastSiteProfileCreatePayload {

    /**
      * A unique identifier for the client performing the mutation.
      */
    def clientMutationId: SelectionBuilder[DastSiteProfileCreatePayload, Option[String]] = Field("clientMutationId", OptionOf(Scalar()))

    /**
      * Errors encountered during execution of the mutation.
      */
    def errors: SelectionBuilder[DastSiteProfileCreatePayload, List[String]] = Field("errors", ListOf(Scalar()))

    /**
      * ID of the site profile.
      */
    def id: SelectionBuilder[DastSiteProfileCreatePayload, Option[String]] = Field("id", OptionOf(Scalar()))
  }

  type DeleteAnnotationPayload

  object DeleteAnnotationPayload {

    /**
      * A unique identifier for the client performing the mutation.
      */
    def clientMutationId: SelectionBuilder[DeleteAnnotationPayload, Option[String]] = Field("clientMutationId", OptionOf(Scalar()))

    /**
      * Errors encountered during execution of the mutation.
      */
    def errors: SelectionBuilder[DeleteAnnotationPayload, List[String]] = Field("errors", ListOf(Scalar()))
  }

  type DeleteJobsResponse

  object DeleteJobsResponse {

    /**
      * Whether or not the entire queue was processed in time; if not, retrying the same request is safe
      */
    def completed: SelectionBuilder[DeleteJobsResponse, Option[Boolean]] = Field("completed", OptionOf(Scalar()))

    /**
      * The number of matching jobs deleted
      */
    def deletedJobs: SelectionBuilder[DeleteJobsResponse, Option[Int]] = Field("deletedJobs", OptionOf(Scalar()))

    /**
      * The queue size after processing
      */
    def queueSize: SelectionBuilder[DeleteJobsResponse, Option[Int]] = Field("queueSize", OptionOf(Scalar()))
  }

  type Design

  object Design {

    /**
      * The diff refs for this design
      */
    def diffRefs[A](innerSelection: SelectionBuilder[DiffRefs, A]): SelectionBuilder[Design, A] = Field("diffRefs", Obj(innerSelection))

    /**
      * All discussions on this noteable
      */
    def discussions[A](
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[DiscussionConnection, A]
    ): SelectionBuilder[Design, A] =
      Field(
        "discussions",
        Obj(innerSelection),
        arguments = List(Argument("after", after), Argument("before", before), Argument("first", first), Argument("last", last))
      )

    /**
      * How this design was changed in the current version
      */
    def event: SelectionBuilder[Design, DesignVersionEvent] = Field("event", Scalar())

    /**
      * The filename of the design
      */
    def filename: SelectionBuilder[Design, String] = Field("filename", Scalar())

    /**
      * The full path to the design file
      */
    def fullPath: SelectionBuilder[Design, String] = Field("fullPath", Scalar())

    /**
      * The ID of this design
      */
    def id: SelectionBuilder[Design, String] = Field("id", Scalar())

    /**
      * The URL of the full-sized image
      */
    def image: SelectionBuilder[Design, String] = Field("image", Scalar())

    /**
      * The URL of the design resized to fit within the bounds of 432x230. This will be `null` if the image has not been generated
      */
    def imageV432x230: SelectionBuilder[Design, Option[String]] = Field("imageV432x230", OptionOf(Scalar()))

    /**
      * The issue the design belongs to
      */
    def issue[A](innerSelection: SelectionBuilder[Issue, A]): SelectionBuilder[Design, A] = Field("issue", Obj(innerSelection))

    /**
      * All notes on this noteable
      */
    def notes[A](
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[NoteConnection, A]
    ): SelectionBuilder[Design, A] =
      Field(
        "notes",
        Obj(innerSelection),
        arguments = List(Argument("after", after), Argument("before", before), Argument("first", first), Argument("last", last))
      )

    /**
      * The total count of user-created notes for this design
      */
    def notesCount: SelectionBuilder[Design, Int] = Field("notesCount", Scalar())

    /**
      * The project the design belongs to
      */
    def project[A](innerSelection: SelectionBuilder[Project, A]): SelectionBuilder[Design, A] = Field("project", Obj(innerSelection))

    /**
      * All versions related to this design ordered newest first
      */
    def versions[A](
      earlierOrEqualToSha: Option[String] = None,
      earlierOrEqualToId: Option[String] = None,
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[DesignVersionConnection, A]
    ): SelectionBuilder[Design, A] =
      Field(
        "versions",
        Obj(innerSelection),
        arguments = List(
          Argument("earlierOrEqualToSha", earlierOrEqualToSha),
          Argument("earlierOrEqualToId", earlierOrEqualToId),
          Argument("after", after),
          Argument("before", before),
          Argument("first", first),
          Argument("last", last)
        )
      )

  }

  type DesignAtVersion

  object DesignAtVersion {

    /**
      * The underlying design.
      */
    def design[A](innerSelection: SelectionBuilder[Design, A]): SelectionBuilder[DesignAtVersion, A] = Field("design", Obj(innerSelection))

    /**
      * The diff refs for this design
      */
    def diffRefs[A](innerSelection: SelectionBuilder[DiffRefs, A]): SelectionBuilder[DesignAtVersion, A] =
      Field("diffRefs", Obj(innerSelection))

    /**
      * How this design was changed in the current version
      */
    def event: SelectionBuilder[DesignAtVersion, DesignVersionEvent] = Field("event", Scalar())

    /**
      * The filename of the design
      */
    def filename: SelectionBuilder[DesignAtVersion, String] = Field("filename", Scalar())

    /**
      * The full path to the design file
      */
    def fullPath: SelectionBuilder[DesignAtVersion, String] = Field("fullPath", Scalar())

    /**
      * The ID of this design
      */
    def id: SelectionBuilder[DesignAtVersion, String] = Field("id", Scalar())

    /**
      * The URL of the full-sized image
      */
    def image: SelectionBuilder[DesignAtVersion, String] = Field("image", Scalar())

    /**
      * The URL of the design resized to fit within the bounds of 432x230. This will be `null` if the image has not been generated
      */
    def imageV432x230: SelectionBuilder[DesignAtVersion, Option[String]] = Field("imageV432x230", OptionOf(Scalar()))

    /**
      * The issue the design belongs to
      */
    def issue[A](innerSelection: SelectionBuilder[Issue, A]): SelectionBuilder[DesignAtVersion, A] = Field("issue", Obj(innerSelection))

    /**
      * The total count of user-created notes for this design
      */
    def notesCount: SelectionBuilder[DesignAtVersion, Int] = Field("notesCount", Scalar())

    /**
      * The project the design belongs to
      */
    def project[A](innerSelection: SelectionBuilder[Project, A]): SelectionBuilder[DesignAtVersion, A] =
      Field("project", Obj(innerSelection))

    /**
      * The version this design-at-versions is pinned to
      */
    def version[A](innerSelection: SelectionBuilder[DesignVersion, A]): SelectionBuilder[DesignAtVersion, A] =
      Field("version", Obj(innerSelection))
  }

  type DesignAtVersionConnection

  object DesignAtVersionConnection {

    /**
      * A list of edges.
      */
    def edges[A](
      innerSelection: SelectionBuilder[DesignAtVersionEdge, A]
    ): SelectionBuilder[DesignAtVersionConnection, Option[List[Option[A]]]] =
      Field("edges", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * A list of nodes.
      */
    def nodes[A](
      innerSelection: SelectionBuilder[DesignAtVersion, A]
    ): SelectionBuilder[DesignAtVersionConnection, Option[List[Option[A]]]] =
      Field("nodes", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * Information to aid in pagination.
      */
    def pageInfo[A](innerSelection: SelectionBuilder[PageInfo, A]): SelectionBuilder[DesignAtVersionConnection, A] =
      Field("pageInfo", Obj(innerSelection))
  }

  type DesignAtVersionEdge

  object DesignAtVersionEdge {

    /**
      * A cursor for use in pagination.
      */
    def cursor: SelectionBuilder[DesignAtVersionEdge, String] = Field("cursor", Scalar())

    /**
      * The item at the end of the edge.
      */
    def node[A](innerSelection: SelectionBuilder[DesignAtVersion, A]): SelectionBuilder[DesignAtVersionEdge, Option[A]] =
      Field("node", OptionOf(Obj(innerSelection)))
  }

  type DesignCollection

  object DesignCollection {

    /**
      * Find a specific design
      */
    def design[A](
      id: Option[String] = None,
      filename: Option[String] = None
    )(
      innerSelection: SelectionBuilder[Design, A]
    ): SelectionBuilder[DesignCollection, Option[A]] =
      Field("design", OptionOf(Obj(innerSelection)), arguments = List(Argument("id", id), Argument("filename", filename)))

    /**
      * Find a design as of a version
      */
    def designAtVersion[A](
      id: String
    )(
      innerSelection: SelectionBuilder[DesignAtVersion, A]
    ): SelectionBuilder[DesignCollection, Option[A]] =
      Field("designAtVersion", OptionOf(Obj(innerSelection)), arguments = List(Argument("id", id)))

    /**
      * All designs for the design collection
      */
    def designs[A](
      ids: Option[List[String]] = None,
      filenames: Option[List[String]] = None,
      atVersion: Option[String] = None,
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[DesignConnection, A]
    ): SelectionBuilder[DesignCollection, A] =
      Field(
        "designs",
        Obj(innerSelection),
        arguments = List(
          Argument("ids", ids),
          Argument("filenames", filenames),
          Argument("atVersion", atVersion),
          Argument("after", after),
          Argument("before", before),
          Argument("first", first),
          Argument("last", last)
        )
      )

    /**
      * Issue associated with the design collection
      */
    def issue[A](innerSelection: SelectionBuilder[Issue, A]): SelectionBuilder[DesignCollection, A] = Field("issue", Obj(innerSelection))

    /**
      * Project associated with the design collection
      */
    def project[A](innerSelection: SelectionBuilder[Project, A]): SelectionBuilder[DesignCollection, A] =
      Field("project", Obj(innerSelection))

    /**
      * A specific version
      */
    def version[A](
      sha: Option[String] = None,
      id: Option[String] = None
    )(
      innerSelection: SelectionBuilder[DesignVersion, A]
    ): SelectionBuilder[DesignCollection, Option[A]] =
      Field("version", OptionOf(Obj(innerSelection)), arguments = List(Argument("sha", sha), Argument("id", id)))

    /**
      * All versions related to all designs, ordered newest first
      */
    def versions[A](
      earlierOrEqualToSha: Option[String] = None,
      earlierOrEqualToId: Option[String] = None,
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[DesignVersionConnection, A]
    ): SelectionBuilder[DesignCollection, A] =
      Field(
        "versions",
        Obj(innerSelection),
        arguments = List(
          Argument("earlierOrEqualToSha", earlierOrEqualToSha),
          Argument("earlierOrEqualToId", earlierOrEqualToId),
          Argument("after", after),
          Argument("before", before),
          Argument("first", first),
          Argument("last", last)
        )
      )

  }

  type DesignConnection

  object DesignConnection {

    /**
      * A list of edges.
      */
    def edges[A](innerSelection: SelectionBuilder[DesignEdge, A]): SelectionBuilder[DesignConnection, Option[List[Option[A]]]] =
      Field("edges", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * A list of nodes.
      */
    def nodes[A](innerSelection: SelectionBuilder[Design, A]): SelectionBuilder[DesignConnection, Option[List[Option[A]]]] =
      Field("nodes", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * Information to aid in pagination.
      */
    def pageInfo[A](innerSelection: SelectionBuilder[PageInfo, A]): SelectionBuilder[DesignConnection, A] =
      Field("pageInfo", Obj(innerSelection))
  }

  type DesignEdge

  object DesignEdge {

    /**
      * A cursor for use in pagination.
      */
    def cursor: SelectionBuilder[DesignEdge, String] = Field("cursor", Scalar())

    /**
      * The item at the end of the edge.
      */
    def node[A](innerSelection: SelectionBuilder[Design, A]): SelectionBuilder[DesignEdge, Option[A]] =
      Field("node", OptionOf(Obj(innerSelection)))
  }

  type DesignManagement

  object DesignManagement {

    /**
      * Find a design as of a version
      */
    def designAtVersion[A](
      id: String
    )(
      innerSelection: SelectionBuilder[DesignAtVersion, A]
    ): SelectionBuilder[DesignManagement, Option[A]] =
      Field("designAtVersion", OptionOf(Obj(innerSelection)), arguments = List(Argument("id", id)))

    /**
      * Find a version
      */
    def version[A](id: String)(innerSelection: SelectionBuilder[DesignVersion, A]): SelectionBuilder[DesignManagement, Option[A]] =
      Field("version", OptionOf(Obj(innerSelection)), arguments = List(Argument("id", id)))
  }

  type DesignManagementDeletePayload

  object DesignManagementDeletePayload {

    /**
      * A unique identifier for the client performing the mutation.
      */
    def clientMutationId: SelectionBuilder[DesignManagementDeletePayload, Option[String]] = Field("clientMutationId", OptionOf(Scalar()))

    /**
      * Errors encountered during execution of the mutation.
      */
    def errors: SelectionBuilder[DesignManagementDeletePayload, List[String]] = Field("errors", ListOf(Scalar()))

    /**
      * The new version in which the designs are deleted
      */
    def version[A](innerSelection: SelectionBuilder[DesignVersion, A]): SelectionBuilder[DesignManagementDeletePayload, Option[A]] =
      Field("version", OptionOf(Obj(innerSelection)))
  }

  type DesignManagementUploadPayload

  object DesignManagementUploadPayload {

    /**
      * A unique identifier for the client performing the mutation.
      */
    def clientMutationId: SelectionBuilder[DesignManagementUploadPayload, Option[String]] = Field("clientMutationId", OptionOf(Scalar()))

    /**
      * The designs that were uploaded by the mutation
      */
    def designs[A](innerSelection: SelectionBuilder[Design, A]): SelectionBuilder[DesignManagementUploadPayload, List[A]] =
      Field("designs", ListOf(Obj(innerSelection)))

    /**
      * Errors encountered during execution of the mutation.
      */
    def errors: SelectionBuilder[DesignManagementUploadPayload, List[String]] = Field("errors", ListOf(Scalar()))

    /**
      * Any designs that were skipped from the upload due to there being no change to their content since their last version
      */
    def skippedDesigns[A](innerSelection: SelectionBuilder[Design, A]): SelectionBuilder[DesignManagementUploadPayload, List[A]] =
      Field("skippedDesigns", ListOf(Obj(innerSelection)))
  }

  type DesignVersion

  object DesignVersion {

    /**
      * A particular design as of this version, provided it is visible at this version
      */
    def designAtVersion[A](
      id: Option[String] = None,
      designId: Option[String] = None,
      filename: Option[String] = None
    )(
      innerSelection: SelectionBuilder[DesignAtVersion, A]
    ): SelectionBuilder[DesignVersion, A] =
      Field(
        "designAtVersion",
        Obj(innerSelection),
        arguments = List(Argument("id", id), Argument("designId", designId), Argument("filename", filename))
      )

    /**
      * All designs that were changed in the version
      */
    def designs[A](
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[DesignConnection, A]
    ): SelectionBuilder[DesignVersion, A] =
      Field(
        "designs",
        Obj(innerSelection),
        arguments = List(Argument("after", after), Argument("before", before), Argument("first", first), Argument("last", last))
      )

    /**
      * All designs that are visible at this version, as of this version
      */
    def designsAtVersion[A](
      ids: Option[List[String]] = None,
      filenames: Option[List[String]] = None,
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[DesignAtVersionConnection, A]
    ): SelectionBuilder[DesignVersion, A] =
      Field(
        "designsAtVersion",
        Obj(innerSelection),
        arguments = List(
          Argument("ids", ids),
          Argument("filenames", filenames),
          Argument("after", after),
          Argument("before", before),
          Argument("first", first),
          Argument("last", last)
        )
      )

    /**
      * ID of the design version
      */
    def id: SelectionBuilder[DesignVersion, String] = Field("id", Scalar())

    /**
      * SHA of the design version
      */
    def sha: SelectionBuilder[DesignVersion, String] = Field("sha", Scalar())
  }

  type DesignVersionConnection

  object DesignVersionConnection {

    /**
      * A list of edges.
      */
    def edges[A](
      innerSelection: SelectionBuilder[DesignVersionEdge, A]
    ): SelectionBuilder[DesignVersionConnection, Option[List[Option[A]]]] = Field("edges", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * A list of nodes.
      */
    def nodes[A](innerSelection: SelectionBuilder[DesignVersion, A]): SelectionBuilder[DesignVersionConnection, Option[List[Option[A]]]] =
      Field("nodes", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * Information to aid in pagination.
      */
    def pageInfo[A](innerSelection: SelectionBuilder[PageInfo, A]): SelectionBuilder[DesignVersionConnection, A] =
      Field("pageInfo", Obj(innerSelection))
  }

  type DesignVersionEdge

  object DesignVersionEdge {

    /**
      * A cursor for use in pagination.
      */
    def cursor: SelectionBuilder[DesignVersionEdge, String] = Field("cursor", Scalar())

    /**
      * The item at the end of the edge.
      */
    def node[A](innerSelection: SelectionBuilder[DesignVersion, A]): SelectionBuilder[DesignVersionEdge, Option[A]] =
      Field("node", OptionOf(Obj(innerSelection)))
  }

  type DestroyNotePayload

  object DestroyNotePayload {

    /**
      * A unique identifier for the client performing the mutation.
      */
    def clientMutationId: SelectionBuilder[DestroyNotePayload, Option[String]] = Field("clientMutationId", OptionOf(Scalar()))

    /**
      * Errors encountered during execution of the mutation.
      */
    def errors: SelectionBuilder[DestroyNotePayload, List[String]] = Field("errors", ListOf(Scalar()))

    /**
      * The note after mutation
      */
    def note[A](innerSelection: SelectionBuilder[Note, A]): SelectionBuilder[DestroyNotePayload, Option[A]] =
      Field("note", OptionOf(Obj(innerSelection)))
  }

  type DestroySnippetPayload

  object DestroySnippetPayload {

    /**
      * A unique identifier for the client performing the mutation.
      */
    def clientMutationId: SelectionBuilder[DestroySnippetPayload, Option[String]] = Field("clientMutationId", OptionOf(Scalar()))

    /**
      * Errors encountered during execution of the mutation.
      */
    def errors: SelectionBuilder[DestroySnippetPayload, List[String]] = Field("errors", ListOf(Scalar()))

    /**
      * The snippet after mutation
      */
    def snippet[A](innerSelection: SelectionBuilder[Snippet, A]): SelectionBuilder[DestroySnippetPayload, Option[A]] =
      Field("snippet", OptionOf(Obj(innerSelection)))
  }

  type DetailedStatus

  object DetailedStatus {

    /**
      * Path of the details for the pipeline status
      */
    def detailsPath: SelectionBuilder[DetailedStatus, String] = Field("detailsPath", Scalar())

    /**
      * Favicon of the pipeline status
      */
    def favicon: SelectionBuilder[DetailedStatus, String] = Field("favicon", Scalar())

    /**
      * Group of the pipeline status
      */
    def group: SelectionBuilder[DetailedStatus, String] = Field("group", Scalar())

    /**
      * Indicates if the pipeline status has further details
      */
    def hasDetails: SelectionBuilder[DetailedStatus, Boolean] = Field("hasDetails", Scalar())

    /**
      * Icon of the pipeline status
      */
    def icon: SelectionBuilder[DetailedStatus, String] = Field("icon", Scalar())

    /**
      * Label of the pipeline status
      */
    def label: SelectionBuilder[DetailedStatus, String] = Field("label", Scalar())

    /**
      * Text of the pipeline status
      */
    def text: SelectionBuilder[DetailedStatus, String] = Field("text", Scalar())

    /**
      * Tooltip associated with the pipeline status
      */
    def tooltip: SelectionBuilder[DetailedStatus, String] = Field("tooltip", Scalar())
  }

  type DiffPosition

  object DiffPosition {

    /**
      * Information about the branch, HEAD, and base at the time of commenting
      */
    def diffRefs[A](innerSelection: SelectionBuilder[DiffRefs, A]): SelectionBuilder[DiffPosition, A] =
      Field("diffRefs", Obj(innerSelection))

    /**
      * Path of the file that was changed
      */
    def filePath: SelectionBuilder[DiffPosition, String] = Field("filePath", Scalar())

    /**
      * Total height of the image
      */
    def height: SelectionBuilder[DiffPosition, Option[Int]] = Field("height", OptionOf(Scalar()))

    /**
      * Line on HEAD SHA that was changed
      */
    def newLine: SelectionBuilder[DiffPosition, Option[Int]] = Field("newLine", OptionOf(Scalar()))

    /**
      * Path of the file on the HEAD SHA
      */
    def newPath: SelectionBuilder[DiffPosition, Option[String]] = Field("newPath", OptionOf(Scalar()))

    /**
      * Line on start SHA that was changed
      */
    def oldLine: SelectionBuilder[DiffPosition, Option[Int]] = Field("oldLine", OptionOf(Scalar()))

    /**
      * Path of the file on the start SHA
      */
    def oldPath: SelectionBuilder[DiffPosition, Option[String]] = Field("oldPath", OptionOf(Scalar()))

    /**
      * Type of file the position refers to
      */
    def positionType: SelectionBuilder[DiffPosition, DiffPositionType] = Field("positionType", Scalar())

    /**
      * Total width of the image
      */
    def width: SelectionBuilder[DiffPosition, Option[Int]] = Field("width", OptionOf(Scalar()))

    /**
      * X position of the note
      */
    def x: SelectionBuilder[DiffPosition, Option[Int]] = Field("x", OptionOf(Scalar()))

    /**
      * Y position of the note
      */
    def y: SelectionBuilder[DiffPosition, Option[Int]] = Field("y", OptionOf(Scalar()))
  }

  type DiffRefs

  object DiffRefs {

    /**
      * Merge base of the branch the comment was made on
      */
    def baseSha: SelectionBuilder[DiffRefs, Option[String]] = Field("baseSha", OptionOf(Scalar()))

    /**
      * SHA of the HEAD at the time the comment was made
      */
    def headSha: SelectionBuilder[DiffRefs, String] = Field("headSha", Scalar())

    /**
      * SHA of the branch being compared against
      */
    def startSha: SelectionBuilder[DiffRefs, String] = Field("startSha", Scalar())
  }

  type DiffStats

  object DiffStats {

    /**
      * Number of lines added to this file
      */
    def additions: SelectionBuilder[DiffStats, Int] = Field("additions", Scalar())

    /**
      * Number of lines deleted from this file
      */
    def deletions: SelectionBuilder[DiffStats, Int] = Field("deletions", Scalar())

    /**
      * File path, relative to repository root
      */
    def path: SelectionBuilder[DiffStats, String] = Field("path", Scalar())
  }

  type DiffStatsSummary

  object DiffStatsSummary {

    /**
      * Number of lines added
      */
    def additions: SelectionBuilder[DiffStatsSummary, Int] = Field("additions", Scalar())

    /**
      * Number of lines changed
      */
    def changes: SelectionBuilder[DiffStatsSummary, Int] = Field("changes", Scalar())

    /**
      * Number of lines deleted
      */
    def deletions: SelectionBuilder[DiffStatsSummary, Int] = Field("deletions", Scalar())

    /**
      * Number of files changed
      */
    def fileCount: SelectionBuilder[DiffStatsSummary, Int] = Field("fileCount", Scalar())
  }

  type Discussion

  object Discussion {

    /**
      * Timestamp of the discussion's creation
      */
    def createdAt: SelectionBuilder[Discussion, Time] = Field("createdAt", Scalar())

    /**
      * ID of this discussion
      */
    def id: SelectionBuilder[Discussion, String] = Field("id", Scalar())

    /**
      * All notes in the discussion
      */
    def notes[A](
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[NoteConnection, A]
    ): SelectionBuilder[Discussion, A] =
      Field(
        "notes",
        Obj(innerSelection),
        arguments = List(Argument("after", after), Argument("before", before), Argument("first", first), Argument("last", last))
      )

    /**
      * ID used to reply to this discussion
      */
    def replyId: SelectionBuilder[Discussion, String] = Field("replyId", Scalar())

    /**
      * Indicates if the object can be resolved
      */
    def resolvable: SelectionBuilder[Discussion, Boolean] = Field("resolvable", Scalar())

    /**
      * Indicates if the object is resolved
      */
    def resolved: SelectionBuilder[Discussion, Boolean] = Field("resolved", Scalar())

    /**
      * Timestamp of when the object was resolved
      */
    def resolvedAt: SelectionBuilder[Discussion, Option[Time]] = Field("resolvedAt", OptionOf(Scalar()))

    /**
      * User who resolved the object
      */
    def resolvedBy[A](innerSelection: SelectionBuilder[User, A]): SelectionBuilder[Discussion, Option[A]] =
      Field("resolvedBy", OptionOf(Obj(innerSelection)))
  }

  type DiscussionConnection

  object DiscussionConnection {

    /**
      * A list of edges.
      */
    def edges[A](innerSelection: SelectionBuilder[DiscussionEdge, A]): SelectionBuilder[DiscussionConnection, Option[List[Option[A]]]] =
      Field("edges", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * A list of nodes.
      */
    def nodes[A](innerSelection: SelectionBuilder[Discussion, A]): SelectionBuilder[DiscussionConnection, Option[List[Option[A]]]] =
      Field("nodes", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * Information to aid in pagination.
      */
    def pageInfo[A](innerSelection: SelectionBuilder[PageInfo, A]): SelectionBuilder[DiscussionConnection, A] =
      Field("pageInfo", Obj(innerSelection))
  }

  type DiscussionEdge

  object DiscussionEdge {

    /**
      * A cursor for use in pagination.
      */
    def cursor: SelectionBuilder[DiscussionEdge, String] = Field("cursor", Scalar())

    /**
      * The item at the end of the edge.
      */
    def node[A](innerSelection: SelectionBuilder[Discussion, A]): SelectionBuilder[DiscussionEdge, Option[A]] =
      Field("node", OptionOf(Obj(innerSelection)))
  }

  type DiscussionToggleResolvePayload

  object DiscussionToggleResolvePayload {

    /**
      * A unique identifier for the client performing the mutation.
      */
    def clientMutationId: SelectionBuilder[DiscussionToggleResolvePayload, Option[String]] = Field("clientMutationId", OptionOf(Scalar()))

    /**
      * The discussion after mutation
      */
    def discussion[A](innerSelection: SelectionBuilder[Discussion, A]): SelectionBuilder[DiscussionToggleResolvePayload, Option[A]] =
      Field("discussion", OptionOf(Obj(innerSelection)))

    /**
      * Errors encountered during execution of the mutation.
      */
    def errors: SelectionBuilder[DiscussionToggleResolvePayload, List[String]] = Field("errors", ListOf(Scalar()))
  }

  type DismissVulnerabilityPayload

  object DismissVulnerabilityPayload {

    /**
      * A unique identifier for the client performing the mutation.
      */
    def clientMutationId: SelectionBuilder[DismissVulnerabilityPayload, Option[String]] = Field("clientMutationId", OptionOf(Scalar()))

    /**
      * Errors encountered during execution of the mutation.
      */
    def errors: SelectionBuilder[DismissVulnerabilityPayload, List[String]] = Field("errors", ListOf(Scalar()))

    /**
      * The vulnerability after dismissal
      */
    def vulnerability[A](innerSelection: SelectionBuilder[Vulnerability, A]): SelectionBuilder[DismissVulnerabilityPayload, Option[A]] =
      Field("vulnerability", OptionOf(Obj(innerSelection)))
  }

  type Environment

  object Environment {

    /**
      * ID of the environment
      */
    def id: SelectionBuilder[Environment, String] = Field("id", Scalar())

    /**
      * Metrics dashboard schema for the environment
      */
    def metricsDashboard[A](path: String)(innerSelection: SelectionBuilder[MetricsDashboard, A]): SelectionBuilder[Environment, Option[A]] =
      Field("metricsDashboard", OptionOf(Obj(innerSelection)), arguments = List(Argument("path", path)))

    /**
      * Human-readable name of the environment
      */
    def name: SelectionBuilder[Environment, String] = Field("name", Scalar())

    /**
      * State of the environment, for example: available/stopped
      */
    def state: SelectionBuilder[Environment, String] = Field("state", Scalar())
  }

  type EnvironmentConnection

  object EnvironmentConnection {

    /**
      * A list of edges.
      */
    def edges[A](innerSelection: SelectionBuilder[EnvironmentEdge, A]): SelectionBuilder[EnvironmentConnection, Option[List[Option[A]]]] =
      Field("edges", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * A list of nodes.
      */
    def nodes[A](innerSelection: SelectionBuilder[Environment, A]): SelectionBuilder[EnvironmentConnection, Option[List[Option[A]]]] =
      Field("nodes", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * Information to aid in pagination.
      */
    def pageInfo[A](innerSelection: SelectionBuilder[PageInfo, A]): SelectionBuilder[EnvironmentConnection, A] =
      Field("pageInfo", Obj(innerSelection))
  }

  type EnvironmentEdge

  object EnvironmentEdge {

    /**
      * A cursor for use in pagination.
      */
    def cursor: SelectionBuilder[EnvironmentEdge, String] = Field("cursor", Scalar())

    /**
      * The item at the end of the edge.
      */
    def node[A](innerSelection: SelectionBuilder[Environment, A]): SelectionBuilder[EnvironmentEdge, Option[A]] =
      Field("node", OptionOf(Obj(innerSelection)))
  }

  type Epic

  object Epic {

    /**
      * Author of the epic
      */
    def author[A](innerSelection: SelectionBuilder[User, A]): SelectionBuilder[Epic, A] = Field("author", Obj(innerSelection))

    /**
      * Children (sub-epics) of the epic
      */
    def children[A](
      startDate: Option[Time] = None,
      endDate: Option[Time] = None,
      iid: Option[String] = None,
      iids: Option[List[String]] = None,
      state: Option[EpicState] = None,
      search: Option[String] = None,
      sort: Option[EpicSort] = None,
      authorUsername: Option[String] = None,
      labelName: Option[List[String]] = None,
      iidStartsWith: Option[String] = None,
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[EpicConnection, A]
    ): SelectionBuilder[Epic, Option[A]] =
      Field(
        "children",
        OptionOf(Obj(innerSelection)),
        arguments = List(
          Argument("startDate", startDate),
          Argument("endDate", endDate),
          Argument("iid", iid),
          Argument("iids", iids),
          Argument("state", state),
          Argument("search", search),
          Argument("sort", sort),
          Argument("authorUsername", authorUsername),
          Argument("labelName", labelName),
          Argument("iidStartsWith", iidStartsWith),
          Argument("after", after),
          Argument("before", before),
          Argument("first", first),
          Argument("last", last)
        )
      )

    /**
      * Timestamp of the epic's closure
      */
    def closedAt: SelectionBuilder[Epic, Option[Time]] = Field("closedAt", OptionOf(Scalar()))

    /**
      * Indicates if the epic is confidential
      */
    def confidential: SelectionBuilder[Epic, Option[Boolean]] = Field("confidential", OptionOf(Scalar()))

    /**
      * Timestamp of the epic's creation
      */
    def createdAt: SelectionBuilder[Epic, Option[Time]] = Field("createdAt", OptionOf(Scalar()))

    /**
      * Number of open and closed descendant epics and issues
      */
    def descendantCounts[A](innerSelection: SelectionBuilder[EpicDescendantCount, A]): SelectionBuilder[Epic, Option[A]] =
      Field("descendantCounts", OptionOf(Obj(innerSelection)))

    /**
      * Total weight of open and closed issues in the epic and its descendants
      */
    def descendantWeightSum[A](innerSelection: SelectionBuilder[EpicDescendantWeights, A]): SelectionBuilder[Epic, Option[A]] =
      Field("descendantWeightSum", OptionOf(Obj(innerSelection)))

    /**
      * Description of the epic
      */
    def description: SelectionBuilder[Epic, Option[String]] = Field("description", OptionOf(Scalar()))

    /**
      * All discussions on this noteable
      */
    def discussions[A](
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[DiscussionConnection, A]
    ): SelectionBuilder[Epic, A] =
      Field(
        "discussions",
        Obj(innerSelection),
        arguments = List(Argument("after", after), Argument("before", before), Argument("first", first), Argument("last", last))
      )

    /**
      * Number of downvotes the epic has received
      */
    def downvotes: SelectionBuilder[Epic, Int] = Field("downvotes", Scalar())

    /**
      * Due date of the epic
      */
    def dueDate: SelectionBuilder[Epic, Option[Time]] = Field("dueDate", OptionOf(Scalar()))

    /**
      * Fixed due date of the epic
      */
    def dueDateFixed: SelectionBuilder[Epic, Option[Time]] = Field("dueDateFixed", OptionOf(Scalar()))

    /**
      * Inherited due date of the epic from milestones
      */
    def dueDateFromMilestones: SelectionBuilder[Epic, Option[Time]] = Field("dueDateFromMilestones", OptionOf(Scalar()))

    /**
      * Indicates if the due date has been manually set
      */
    def dueDateIsFixed: SelectionBuilder[Epic, Option[Boolean]] = Field("dueDateIsFixed", OptionOf(Scalar()))

    /**
      * Group to which the epic belongs
      */
    def group[A](innerSelection: SelectionBuilder[Group, A]): SelectionBuilder[Epic, A] = Field("group", Obj(innerSelection))

    /**
      * Indicates if the epic has children
      */
    def hasChildren: SelectionBuilder[Epic, Boolean] = Field("hasChildren", Scalar())

    /**
      * Indicates if the epic has direct issues
      */
    def hasIssues: SelectionBuilder[Epic, Boolean] = Field("hasIssues", Scalar())

    /**
      * Indicates if the epic has a parent epic
      */
    def hasParent: SelectionBuilder[Epic, Boolean] = Field("hasParent", Scalar())

    /**
      * Current health status of the epic
      */
    def healthStatus[A](innerSelection: SelectionBuilder[EpicHealthStatus, A]): SelectionBuilder[Epic, Option[A]] =
      Field("healthStatus", OptionOf(Obj(innerSelection)))

    /**
      * ID of the epic
      */
    def id: SelectionBuilder[Epic, String] = Field("id", Scalar())

    /**
      * Internal ID of the epic
      */
    def iid: SelectionBuilder[Epic, String] = Field("iid", Scalar())

    /**
      * A list of issues associated with the epic
      */
    def issues[A](
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[EpicIssueConnection, A]
    ): SelectionBuilder[Epic, Option[A]] =
      Field(
        "issues",
        OptionOf(Obj(innerSelection)),
        arguments = List(Argument("after", after), Argument("before", before), Argument("first", first), Argument("last", last))
      )

    /**
      * Labels assigned to the epic
      */
    def labels[A](
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[LabelConnection, A]
    ): SelectionBuilder[Epic, Option[A]] =
      Field(
        "labels",
        OptionOf(Obj(innerSelection)),
        arguments = List(Argument("after", after), Argument("before", before), Argument("first", first), Argument("last", last))
      )

    /**
      * All notes on this noteable
      */
    def notes[A](
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[NoteConnection, A]
    ): SelectionBuilder[Epic, A] =
      Field(
        "notes",
        Obj(innerSelection),
        arguments = List(Argument("after", after), Argument("before", before), Argument("first", first), Argument("last", last))
      )

    /**
      * Parent epic of the epic
      */
    def parent[A](innerSelection: SelectionBuilder[Epic, A]): SelectionBuilder[Epic, Option[A]] =
      Field("parent", OptionOf(Obj(innerSelection)))

    /**
      * List of participants for the epic
      */
    def participants[A](
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[UserConnection, A]
    ): SelectionBuilder[Epic, Option[A]] =
      Field(
        "participants",
        OptionOf(Obj(innerSelection)),
        arguments = List(Argument("after", after), Argument("before", before), Argument("first", first), Argument("last", last))
      )

    /**
      * Internal reference of the epic. Returned in shortened format by default
      */
    def reference(full: Option[Boolean] = None): SelectionBuilder[Epic, String] =
      Field("reference", Scalar(), arguments = List(Argument("full", full)))

    /**
      * URI path of the epic-issue relationship
      */
    def relationPath: SelectionBuilder[Epic, Option[String]] = Field("relationPath", OptionOf(Scalar()))

    /**
      * The relative position of the epic in the epic tree
      */
    def relativePosition: SelectionBuilder[Epic, Option[Int]] = Field("relativePosition", OptionOf(Scalar()))

    /**
      * Start date of the epic
      */
    def startDate: SelectionBuilder[Epic, Option[Time]] = Field("startDate", OptionOf(Scalar()))

    /**
      * Fixed start date of the epic
      */
    def startDateFixed: SelectionBuilder[Epic, Option[Time]] = Field("startDateFixed", OptionOf(Scalar()))

    /**
      * Inherited start date of the epic from milestones
      */
    def startDateFromMilestones: SelectionBuilder[Epic, Option[Time]] = Field("startDateFromMilestones", OptionOf(Scalar()))

    /**
      * Indicates if the start date has been manually set
      */
    def startDateIsFixed: SelectionBuilder[Epic, Option[Boolean]] = Field("startDateIsFixed", OptionOf(Scalar()))

    /**
      * State of the epic
      */
    def state: SelectionBuilder[Epic, EpicState] = Field("state", Scalar())

    /**
      * Indicates the currently logged in user is subscribed to the epic
      */
    def subscribed: SelectionBuilder[Epic, Boolean] = Field("subscribed", Scalar())

    /**
      * Title of the epic
      */
    def title: SelectionBuilder[Epic, Option[String]] = Field("title", OptionOf(Scalar()))

    /**
      * Timestamp of the epic's last activity
      */
    def updatedAt: SelectionBuilder[Epic, Option[Time]] = Field("updatedAt", OptionOf(Scalar()))

    /**
      * Number of upvotes the epic has received
      */
    def upvotes: SelectionBuilder[Epic, Int] = Field("upvotes", Scalar())

    /**
      * Permissions for the current user on the resource
      */
    def userPermissions[A](innerSelection: SelectionBuilder[EpicPermissions, A]): SelectionBuilder[Epic, A] =
      Field("userPermissions", Obj(innerSelection))

    /**
      * Web path of the epic
      */
    def webPath: SelectionBuilder[Epic, String] = Field("webPath", Scalar())

    /**
      * Web URL of the epic
      */
    def webUrl: SelectionBuilder[Epic, String] = Field("webUrl", Scalar())
  }

  type EpicAddIssuePayload

  object EpicAddIssuePayload {

    /**
      * A unique identifier for the client performing the mutation.
      */
    def clientMutationId: SelectionBuilder[EpicAddIssuePayload, Option[String]] = Field("clientMutationId", OptionOf(Scalar()))

    /**
      * The epic after mutation
      */
    def epic[A](innerSelection: SelectionBuilder[Epic, A]): SelectionBuilder[EpicAddIssuePayload, Option[A]] =
      Field("epic", OptionOf(Obj(innerSelection)))

    /**
      * The epic-issue relation
      */
    def epicIssue[A](innerSelection: SelectionBuilder[EpicIssue, A]): SelectionBuilder[EpicAddIssuePayload, Option[A]] =
      Field("epicIssue", OptionOf(Obj(innerSelection)))

    /**
      * Errors encountered during execution of the mutation.
      */
    def errors: SelectionBuilder[EpicAddIssuePayload, List[String]] = Field("errors", ListOf(Scalar()))
  }

  type EpicConnection

  object EpicConnection {

    /**
      * A list of edges.
      */
    def edges[A](innerSelection: SelectionBuilder[EpicEdge, A]): SelectionBuilder[EpicConnection, Option[List[Option[A]]]] =
      Field("edges", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * A list of nodes.
      */
    def nodes[A](innerSelection: SelectionBuilder[Epic, A]): SelectionBuilder[EpicConnection, Option[List[Option[A]]]] =
      Field("nodes", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * Information to aid in pagination.
      */
    def pageInfo[A](innerSelection: SelectionBuilder[PageInfo, A]): SelectionBuilder[EpicConnection, A] =
      Field("pageInfo", Obj(innerSelection))
  }

  type EpicDescendantCount

  object EpicDescendantCount {

    /**
      * Number of closed sub-epics
      */
    def closedEpics: SelectionBuilder[EpicDescendantCount, Option[Int]] = Field("closedEpics", OptionOf(Scalar()))

    /**
      * Number of closed epic issues
      */
    def closedIssues: SelectionBuilder[EpicDescendantCount, Option[Int]] = Field("closedIssues", OptionOf(Scalar()))

    /**
      * Number of opened sub-epics
      */
    def openedEpics: SelectionBuilder[EpicDescendantCount, Option[Int]] = Field("openedEpics", OptionOf(Scalar()))

    /**
      * Number of opened epic issues
      */
    def openedIssues: SelectionBuilder[EpicDescendantCount, Option[Int]] = Field("openedIssues", OptionOf(Scalar()))
  }

  type EpicDescendantWeights

  object EpicDescendantWeights {

    /**
      * Total weight of completed (closed) issues in this epic, including epic descendants
      */
    def closedIssues: SelectionBuilder[EpicDescendantWeights, Option[Int]] = Field("closedIssues", OptionOf(Scalar()))

    /**
      * Total weight of opened issues in this epic, including epic descendants
      */
    def openedIssues: SelectionBuilder[EpicDescendantWeights, Option[Int]] = Field("openedIssues", OptionOf(Scalar()))
  }

  type EpicEdge

  object EpicEdge {

    /**
      * A cursor for use in pagination.
      */
    def cursor: SelectionBuilder[EpicEdge, String] = Field("cursor", Scalar())

    /**
      * The item at the end of the edge.
      */
    def node[A](innerSelection: SelectionBuilder[Epic, A]): SelectionBuilder[EpicEdge, Option[A]] =
      Field("node", OptionOf(Obj(innerSelection)))
  }

  type EpicHealthStatus

  object EpicHealthStatus {

    /**
      * Number of issues at risk
      */
    def issuesAtRisk: SelectionBuilder[EpicHealthStatus, Option[Int]] = Field("issuesAtRisk", OptionOf(Scalar()))

    /**
      * Number of issues that need attention
      */
    def issuesNeedingAttention: SelectionBuilder[EpicHealthStatus, Option[Int]] = Field("issuesNeedingAttention", OptionOf(Scalar()))

    /**
      * Number of issues on track
      */
    def issuesOnTrack: SelectionBuilder[EpicHealthStatus, Option[Int]] = Field("issuesOnTrack", OptionOf(Scalar()))
  }

  type EpicIssue

  object EpicIssue {

    /**
      * Assignees of the issue
      */
    def assignees[A](
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[UserConnection, A]
    ): SelectionBuilder[EpicIssue, Option[A]] =
      Field(
        "assignees",
        OptionOf(Obj(innerSelection)),
        arguments = List(Argument("after", after), Argument("before", before), Argument("first", first), Argument("last", last))
      )

    /**
      * User that created the issue
      */
    def author[A](innerSelection: SelectionBuilder[User, A]): SelectionBuilder[EpicIssue, A] = Field("author", Obj(innerSelection))

    /**
      * Indicates the issue is blocked
      */
    def blocked: SelectionBuilder[EpicIssue, Boolean] = Field("blocked", Scalar())

    /**
      * Timestamp of when the issue was closed
      */
    def closedAt: SelectionBuilder[EpicIssue, Option[Time]] = Field("closedAt", OptionOf(Scalar()))

    /**
      * Indicates the issue is confidential
      */
    def confidential: SelectionBuilder[EpicIssue, Boolean] = Field("confidential", Scalar())

    /**
      * Timestamp of when the issue was created
      */
    def createdAt: SelectionBuilder[EpicIssue, Time] = Field("createdAt", Scalar())

    /**
      * Description of the issue
      */
    def description: SelectionBuilder[EpicIssue, Option[String]] = Field("description", OptionOf(Scalar()))

    /**
      * The GitLab Flavored Markdown rendering of `description`
      */
    def descriptionHtml: SelectionBuilder[EpicIssue, Option[String]] = Field("descriptionHtml", OptionOf(Scalar()))

    /**
      * Collection of design images associated with this issue
      */
    def designCollection[A](innerSelection: SelectionBuilder[DesignCollection, A]): SelectionBuilder[EpicIssue, Option[A]] =
      Field("designCollection", OptionOf(Obj(innerSelection)))

    /**
      * The designs associated with this issue. Deprecated in 12.2: Use `designCollection`
      */
    @deprecated("Use `designCollection`. Deprecated in 12.2", "")
    def designs[A](innerSelection: SelectionBuilder[DesignCollection, A]): SelectionBuilder[EpicIssue, Option[A]] =
      Field("designs", OptionOf(Obj(innerSelection)))

    /**
      * Indicates discussion is locked on the issue
      */
    def discussionLocked: SelectionBuilder[EpicIssue, Boolean] = Field("discussionLocked", Scalar())

    /**
      * All discussions on this noteable
      */
    def discussions[A](
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[DiscussionConnection, A]
    ): SelectionBuilder[EpicIssue, A] =
      Field(
        "discussions",
        Obj(innerSelection),
        arguments = List(Argument("after", after), Argument("before", before), Argument("first", first), Argument("last", last))
      )

    /**
      * Number of downvotes the issue has received
      */
    def downvotes: SelectionBuilder[EpicIssue, Int] = Field("downvotes", Scalar())

    /**
      * Due date of the issue
      */
    def dueDate: SelectionBuilder[EpicIssue, Option[Time]] = Field("dueDate", OptionOf(Scalar()))

    /**
      * Epic to which this issue belongs
      */
    def epic[A](innerSelection: SelectionBuilder[Epic, A]): SelectionBuilder[EpicIssue, Option[A]] =
      Field("epic", OptionOf(Obj(innerSelection)))

    /**
      * ID of the epic-issue relation
      */
    def epicIssueId: SelectionBuilder[EpicIssue, String] = Field("epicIssueId", Scalar())

    /**
      * Current health status. Returns null if `save_issuable_health_status` feature flag is disabled.
      */
    def healthStatus: SelectionBuilder[EpicIssue, Option[HealthStatus]] = Field("healthStatus", OptionOf(Scalar()))

    /**
      * Global ID of the epic-issue relation
      */
    def id: SelectionBuilder[EpicIssue, Option[String]] = Field("id", OptionOf(Scalar()))

    /**
      * Internal ID of the issue
      */
    def iid: SelectionBuilder[EpicIssue, String] = Field("iid", Scalar())

    /**
      * Iteration of the issue
      */
    def iteration[A](innerSelection: SelectionBuilder[Iteration, A]): SelectionBuilder[EpicIssue, Option[A]] =
      Field("iteration", OptionOf(Obj(innerSelection)))

    /**
      * Labels of the issue
      */
    def labels[A](
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[LabelConnection, A]
    ): SelectionBuilder[EpicIssue, Option[A]] =
      Field(
        "labels",
        OptionOf(Obj(innerSelection)),
        arguments = List(Argument("after", after), Argument("before", before), Argument("first", first), Argument("last", last))
      )

    /**
      * Milestone of the issue
      */
    def milestone[A](innerSelection: SelectionBuilder[Milestone, A]): SelectionBuilder[EpicIssue, Option[A]] =
      Field("milestone", OptionOf(Obj(innerSelection)))

    /**
      * All notes on this noteable
      */
    def notes[A](
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[NoteConnection, A]
    ): SelectionBuilder[EpicIssue, A] =
      Field(
        "notes",
        Obj(innerSelection),
        arguments = List(Argument("after", after), Argument("before", before), Argument("first", first), Argument("last", last))
      )

    /**
      * List of participants in the issue
      */
    def participants[A](
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[UserConnection, A]
    ): SelectionBuilder[EpicIssue, Option[A]] =
      Field(
        "participants",
        OptionOf(Obj(innerSelection)),
        arguments = List(Argument("after", after), Argument("before", before), Argument("first", first), Argument("last", last))
      )

    /**
      * Internal reference of the issue. Returned in shortened format by default
      */
    def reference(full: Option[Boolean] = None): SelectionBuilder[EpicIssue, String] =
      Field("reference", Scalar(), arguments = List(Argument("full", full)))

    /**
      * URI path of the epic-issue relation
      */
    def relationPath: SelectionBuilder[EpicIssue, Option[String]] = Field("relationPath", OptionOf(Scalar()))

    /**
      * Relative position of the issue (used for positioning in epic tree and issue boards)
      */
    def relativePosition: SelectionBuilder[EpicIssue, Option[Int]] = Field("relativePosition", OptionOf(Scalar()))

    /**
      * State of the issue
      */
    def state: SelectionBuilder[EpicIssue, IssueState] = Field("state", Scalar())

    /**
      * Indicates the currently logged in user is subscribed to the issue
      */
    def subscribed: SelectionBuilder[EpicIssue, Boolean] = Field("subscribed", Scalar())

    /**
      * Task completion status of the issue
      */
    def taskCompletionStatus[A](innerSelection: SelectionBuilder[TaskCompletionStatus, A]): SelectionBuilder[EpicIssue, A] =
      Field("taskCompletionStatus", Obj(innerSelection))

    /**
      * Time estimate of the issue
      */
    def timeEstimate: SelectionBuilder[EpicIssue, Int] = Field("timeEstimate", Scalar())

    /**
      * Title of the issue
      */
    def title: SelectionBuilder[EpicIssue, String] = Field("title", Scalar())

    /**
      * The GitLab Flavored Markdown rendering of `title`
      */
    def titleHtml: SelectionBuilder[EpicIssue, Option[String]] = Field("titleHtml", OptionOf(Scalar()))

    /**
      * Total time reported as spent on the issue
      */
    def totalTimeSpent: SelectionBuilder[EpicIssue, Int] = Field("totalTimeSpent", Scalar())

    /**
      * Timestamp of when the issue was last updated
      */
    def updatedAt: SelectionBuilder[EpicIssue, Time] = Field("updatedAt", Scalar())

    /**
      * Number of upvotes the issue has received
      */
    def upvotes: SelectionBuilder[EpicIssue, Int] = Field("upvotes", Scalar())

    /**
      * Number of user notes of the issue
      */
    def userNotesCount: SelectionBuilder[EpicIssue, Int] = Field("userNotesCount", Scalar())

    /**
      * Permissions for the current user on the resource
      */
    def userPermissions[A](innerSelection: SelectionBuilder[IssuePermissions, A]): SelectionBuilder[EpicIssue, A] =
      Field("userPermissions", Obj(innerSelection))

    /**
      * Web path of the issue
      */
    def webPath: SelectionBuilder[EpicIssue, String] = Field("webPath", Scalar())

    /**
      * Web URL of the issue
      */
    def webUrl: SelectionBuilder[EpicIssue, String] = Field("webUrl", Scalar())

    /**
      * Weight of the issue
      */
    def weight: SelectionBuilder[EpicIssue, Option[Int]] = Field("weight", OptionOf(Scalar()))
  }

  type EpicIssueConnection

  object EpicIssueConnection {

    /**
      * Total count of collection
      */
    def count: SelectionBuilder[EpicIssueConnection, Int] = Field("count", Scalar())

    /**
      * A list of edges.
      */
    def edges[A](innerSelection: SelectionBuilder[EpicIssueEdge, A]): SelectionBuilder[EpicIssueConnection, Option[List[Option[A]]]] =
      Field("edges", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * A list of nodes.
      */
    def nodes[A](innerSelection: SelectionBuilder[EpicIssue, A]): SelectionBuilder[EpicIssueConnection, Option[List[Option[A]]]] =
      Field("nodes", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * Information to aid in pagination.
      */
    def pageInfo[A](innerSelection: SelectionBuilder[PageInfo, A]): SelectionBuilder[EpicIssueConnection, A] =
      Field("pageInfo", Obj(innerSelection))
  }

  type EpicIssueEdge

  object EpicIssueEdge {

    /**
      * A cursor for use in pagination.
      */
    def cursor: SelectionBuilder[EpicIssueEdge, String] = Field("cursor", Scalar())

    /**
      * The item at the end of the edge.
      */
    def node[A](innerSelection: SelectionBuilder[EpicIssue, A]): SelectionBuilder[EpicIssueEdge, Option[A]] =
      Field("node", OptionOf(Obj(innerSelection)))
  }

  type EpicPermissions

  object EpicPermissions {

    /**
      * Indicates the user can perform `admin_epic` on this resource
      */
    def adminEpic: SelectionBuilder[EpicPermissions, Boolean] = Field("adminEpic", Scalar())

    /**
      * Indicates the user can perform `award_emoji` on this resource
      */
    def awardEmoji: SelectionBuilder[EpicPermissions, Boolean] = Field("awardEmoji", Scalar())

    /**
      * Indicates the user can perform `create_epic` on this resource
      */
    def createEpic: SelectionBuilder[EpicPermissions, Boolean] = Field("createEpic", Scalar())

    /**
      * Indicates the user can perform `create_note` on this resource
      */
    def createNote: SelectionBuilder[EpicPermissions, Boolean] = Field("createNote", Scalar())

    /**
      * Indicates the user can perform `destroy_epic` on this resource
      */
    def destroyEpic: SelectionBuilder[EpicPermissions, Boolean] = Field("destroyEpic", Scalar())

    /**
      * Indicates the user can perform `read_epic` on this resource
      */
    def readEpic: SelectionBuilder[EpicPermissions, Boolean] = Field("readEpic", Scalar())

    /**
      * Indicates the user can perform `read_epic_iid` on this resource
      */
    def readEpicIid: SelectionBuilder[EpicPermissions, Boolean] = Field("readEpicIid", Scalar())

    /**
      * Indicates the user can perform `update_epic` on this resource
      */
    def updateEpic: SelectionBuilder[EpicPermissions, Boolean] = Field("updateEpic", Scalar())
  }

  type EpicSetSubscriptionPayload

  object EpicSetSubscriptionPayload {

    /**
      * A unique identifier for the client performing the mutation.
      */
    def clientMutationId: SelectionBuilder[EpicSetSubscriptionPayload, Option[String]] = Field("clientMutationId", OptionOf(Scalar()))

    /**
      * The epic after mutation
      */
    def epic[A](innerSelection: SelectionBuilder[Epic, A]): SelectionBuilder[EpicSetSubscriptionPayload, Option[A]] =
      Field("epic", OptionOf(Obj(innerSelection)))

    /**
      * Errors encountered during execution of the mutation.
      */
    def errors: SelectionBuilder[EpicSetSubscriptionPayload, List[String]] = Field("errors", ListOf(Scalar()))
  }

  type EpicTreeReorderPayload

  object EpicTreeReorderPayload {

    /**
      * A unique identifier for the client performing the mutation.
      */
    def clientMutationId: SelectionBuilder[EpicTreeReorderPayload, Option[String]] = Field("clientMutationId", OptionOf(Scalar()))

    /**
      * Errors encountered during execution of the mutation.
      */
    def errors: SelectionBuilder[EpicTreeReorderPayload, List[String]] = Field("errors", ListOf(Scalar()))
  }

  type GeoNode

  object GeoNode {

    /**
      * The maximum concurrency of container repository sync for this secondary node
      */
    def containerRepositoriesMaxCapacity: SelectionBuilder[GeoNode, Option[Int]] =
      Field("containerRepositoriesMaxCapacity", OptionOf(Scalar()))

    /**
      * Indicates whether this Geo node is enabled
      */
    def enabled: SelectionBuilder[GeoNode, Option[Boolean]] = Field("enabled", OptionOf(Scalar()))

    /**
      * The maximum concurrency of LFS/attachment backfill for this secondary node
      */
    def filesMaxCapacity: SelectionBuilder[GeoNode, Option[Int]] = Field("filesMaxCapacity", OptionOf(Scalar()))

    /**
      * ID of this GeoNode
      */
    def id: SelectionBuilder[GeoNode, String] = Field("id", Scalar())

    /**
      * The URL defined on the primary node that secondary nodes should use to contact it
      */
    def internalUrl: SelectionBuilder[GeoNode, Option[String]] = Field("internalUrl", OptionOf(Scalar()))

    /**
      * The interval (in days) in which the repository verification is valid. Once expired, it will be reverified
      */
    def minimumReverificationInterval: SelectionBuilder[GeoNode, Option[Int]] = Field("minimumReverificationInterval", OptionOf(Scalar()))

    /**
      * The unique identifier for this Geo node
      */
    def name: SelectionBuilder[GeoNode, Option[String]] = Field("name", OptionOf(Scalar()))

    /**
      * Indicates whether this Geo node is the primary
      */
    def primary: SelectionBuilder[GeoNode, Option[Boolean]] = Field("primary", OptionOf(Scalar()))

    /**
      * The maximum concurrency of repository backfill for this secondary node
      */
    def reposMaxCapacity: SelectionBuilder[GeoNode, Option[Int]] = Field("reposMaxCapacity", OptionOf(Scalar()))

    /**
      * The namespaces that should be synced, if `selective_sync_type` == `namespaces`
      */
    def selectiveSyncNamespaces[A](
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[NamespaceConnection, A]
    ): SelectionBuilder[GeoNode, Option[A]] =
      Field(
        "selectiveSyncNamespaces",
        OptionOf(Obj(innerSelection)),
        arguments = List(Argument("after", after), Argument("before", before), Argument("first", first), Argument("last", last))
      )

    /**
      * The repository storages whose projects should be synced, if `selective_sync_type` == `shards`
      */
    def selectiveSyncShards: SelectionBuilder[GeoNode, Option[List[String]]] = Field("selectiveSyncShards", OptionOf(ListOf(Scalar())))

    /**
      * Indicates if syncing is limited to only specific groups, or shards
      */
    def selectiveSyncType: SelectionBuilder[GeoNode, Option[String]] = Field("selectiveSyncType", OptionOf(Scalar()))

    /**
      * Indicates if this secondary node will replicate blobs in Object Storage
      */
    def syncObjectStorage: SelectionBuilder[GeoNode, Option[Boolean]] = Field("syncObjectStorage", OptionOf(Scalar()))

    /**
      * The user-facing URL for this Geo node
      */
    def url: SelectionBuilder[GeoNode, Option[String]] = Field("url", OptionOf(Scalar()))

    /**
      * The maximum concurrency of repository verification for this secondary node
      */
    def verificationMaxCapacity: SelectionBuilder[GeoNode, Option[Int]] = Field("verificationMaxCapacity", OptionOf(Scalar()))
  }

  type GrafanaIntegration

  object GrafanaIntegration {

    /**
      * Timestamp of the issue's creation
      */
    def createdAt: SelectionBuilder[GrafanaIntegration, Time] = Field("createdAt", Scalar())

    /**
      * Indicates whether Grafana integration is enabled
      */
    def enabled: SelectionBuilder[GrafanaIntegration, Boolean] = Field("enabled", Scalar())

    /**
      * URL for the Grafana host for the Grafana integration
      */
    def grafanaUrl: SelectionBuilder[GrafanaIntegration, String] = Field("grafanaUrl", Scalar())

    /**
      * Internal ID of the Grafana integration
      */
    def id: SelectionBuilder[GrafanaIntegration, String] = Field("id", Scalar())

    /**
      * API token for the Grafana integration. Deprecated in 12.7: Plain text token has been masked for security reasons
      */
    @deprecated("Plain text token has been masked for security reasons. Deprecated in 12.7", "")
    def token: SelectionBuilder[GrafanaIntegration, String] = Field("token", Scalar())

    /**
      * Timestamp of the issue's last activity
      */
    def updatedAt: SelectionBuilder[GrafanaIntegration, Time] = Field("updatedAt", Scalar())
  }

  type Group

  object Group {

    /**
      * Indicates whether Auto DevOps is enabled for all projects within this group
      */
    def autoDevopsEnabled: SelectionBuilder[Group, Option[Boolean]] = Field("autoDevopsEnabled", OptionOf(Scalar()))

    /**
      * Avatar URL of the group
      */
    def avatarUrl: SelectionBuilder[Group, Option[String]] = Field("avatarUrl", OptionOf(Scalar()))

    /**
      * A single board of the group
      */
    def board[A](id: Option[String] = None)(innerSelection: SelectionBuilder[Board, A]): SelectionBuilder[Group, Option[A]] =
      Field("board", OptionOf(Obj(innerSelection)), arguments = List(Argument("id", id)))

    /**
      * Boards of the group
      */
    def boards[A](
      id: Option[String] = None,
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[BoardConnection, A]
    ): SelectionBuilder[Group, Option[A]] =
      Field(
        "boards",
        OptionOf(Obj(innerSelection)),
        arguments =
          List(Argument("id", id), Argument("after", after), Argument("before", before), Argument("first", first), Argument("last", last))
      )

    /**
      * Description of the namespace
      */
    def description: SelectionBuilder[Group, Option[String]] = Field("description", OptionOf(Scalar()))

    /**
      * The GitLab Flavored Markdown rendering of `description`
      */
    def descriptionHtml: SelectionBuilder[Group, Option[String]] = Field("descriptionHtml", OptionOf(Scalar()))

    /**
      * Indicates if a group has email notifications disabled
      */
    def emailsDisabled: SelectionBuilder[Group, Option[Boolean]] = Field("emailsDisabled", OptionOf(Scalar()))

    /**
      * Find a single epic
      */
    def epic[A](
      startDate: Option[Time] = None,
      endDate: Option[Time] = None,
      iid: Option[String] = None,
      iids: Option[List[String]] = None,
      state: Option[EpicState] = None,
      search: Option[String] = None,
      sort: Option[EpicSort] = None,
      authorUsername: Option[String] = None,
      labelName: Option[List[String]] = None,
      iidStartsWith: Option[String] = None
    )(
      innerSelection: SelectionBuilder[Epic, A]
    ): SelectionBuilder[Group, Option[A]] =
      Field(
        "epic",
        OptionOf(Obj(innerSelection)),
        arguments = List(
          Argument("startDate", startDate),
          Argument("endDate", endDate),
          Argument("iid", iid),
          Argument("iids", iids),
          Argument("state", state),
          Argument("search", search),
          Argument("sort", sort),
          Argument("authorUsername", authorUsername),
          Argument("labelName", labelName),
          Argument("iidStartsWith", iidStartsWith)
        )
      )

    /**
      * Find epics
      */
    def epics[A](
      startDate: Option[Time] = None,
      endDate: Option[Time] = None,
      iid: Option[String] = None,
      iids: Option[List[String]] = None,
      state: Option[EpicState] = None,
      search: Option[String] = None,
      sort: Option[EpicSort] = None,
      authorUsername: Option[String] = None,
      labelName: Option[List[String]] = None,
      iidStartsWith: Option[String] = None,
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[EpicConnection, A]
    ): SelectionBuilder[Group, Option[A]] =
      Field(
        "epics",
        OptionOf(Obj(innerSelection)),
        arguments = List(
          Argument("startDate", startDate),
          Argument("endDate", endDate),
          Argument("iid", iid),
          Argument("iids", iids),
          Argument("state", state),
          Argument("search", search),
          Argument("sort", sort),
          Argument("authorUsername", authorUsername),
          Argument("labelName", labelName),
          Argument("iidStartsWith", iidStartsWith),
          Argument("after", after),
          Argument("before", before),
          Argument("first", first),
          Argument("last", last)
        )
      )

    /**
      * Indicates if Epics are enabled for namespace
      */
    def epicsEnabled: SelectionBuilder[Group, Option[Boolean]] = Field("epicsEnabled", OptionOf(Scalar()))

    /**
      * Full name of the namespace
      */
    def fullName: SelectionBuilder[Group, String] = Field("fullName", Scalar())

    /**
      * Full path of the namespace
      */
    def fullPath: SelectionBuilder[Group, String] = Field("fullPath", Scalar())

    /**
      * Indicates if Group timelogs are enabled for namespace
      */
    def groupTimelogsEnabled: SelectionBuilder[Group, Option[Boolean]] = Field("groupTimelogsEnabled", OptionOf(Scalar()))

    /**
      * ID of the namespace
      */
    def id: SelectionBuilder[Group, String] = Field("id", Scalar())

    /**
      * Status of the temporary storage increase
      */
    def isTemporaryStorageIncreaseEnabled: SelectionBuilder[Group, Boolean] = Field("isTemporaryStorageIncreaseEnabled", Scalar())

    /**
      * Issues of the group
      */
    def issues[A](
      iid: Option[String] = None,
      iids: Option[List[String]] = None,
      state: Option[IssuableState] = None,
      labelName: Option[List[Option[String]]] = None,
      milestoneTitle: Option[List[Option[String]]] = None,
      assigneeUsername: Option[String] = None,
      assigneeId: Option[String] = None,
      createdBefore: Option[Time] = None,
      createdAfter: Option[Time] = None,
      updatedBefore: Option[Time] = None,
      updatedAfter: Option[Time] = None,
      closedBefore: Option[Time] = None,
      closedAfter: Option[Time] = None,
      search: Option[String] = None,
      sort: Option[IssueSort] = None,
      iterationId: Option[List[Option[String]]] = None,
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[IssueConnection, A]
    ): SelectionBuilder[Group, Option[A]] =
      Field(
        "issues",
        OptionOf(Obj(innerSelection)),
        arguments = List(
          Argument("iid", iid),
          Argument("iids", iids),
          Argument("state", state),
          Argument("labelName", labelName),
          Argument("milestoneTitle", milestoneTitle),
          Argument("assigneeUsername", assigneeUsername),
          Argument("assigneeId", assigneeId),
          Argument("createdBefore", createdBefore),
          Argument("createdAfter", createdAfter),
          Argument("updatedBefore", updatedBefore),
          Argument("updatedAfter", updatedAfter),
          Argument("closedBefore", closedBefore),
          Argument("closedAfter", closedAfter),
          Argument("search", search),
          Argument("sort", sort),
          Argument("iterationId", iterationId),
          Argument("after", after),
          Argument("before", before),
          Argument("first", first),
          Argument("last", last)
        )
      )

    /**
      * Find iterations
      */
    def iterations[A](
      startDate: Option[Time] = None,
      endDate: Option[Time] = None,
      state: Option[IterationState] = None,
      title: Option[String] = None,
      id: Option[String] = None,
      iid: Option[String] = None,
      includeAncestors: Option[Boolean] = None,
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[IterationConnection, A]
    ): SelectionBuilder[Group, Option[A]] =
      Field(
        "iterations",
        OptionOf(Obj(innerSelection)),
        arguments = List(
          Argument("startDate", startDate),
          Argument("endDate", endDate),
          Argument("state", state),
          Argument("title", title),
          Argument("id", id),
          Argument("iid", iid),
          Argument("includeAncestors", includeAncestors),
          Argument("after", after),
          Argument("before", before),
          Argument("first", first),
          Argument("last", last)
        )
      )

    /**
      * A label available on this group
      */
    def label[A](title: String)(innerSelection: SelectionBuilder[Label, A]): SelectionBuilder[Group, Option[A]] =
      Field("label", OptionOf(Obj(innerSelection)), arguments = List(Argument("title", title)))

    /**
      * Labels available on this group
      */
    def labels[A](
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None,
      searchTerm: Option[String] = None
    )(
      innerSelection: SelectionBuilder[LabelConnection, A]
    ): SelectionBuilder[Group, Option[A]] =
      Field(
        "labels",
        OptionOf(Obj(innerSelection)),
        arguments = List(
          Argument("after", after),
          Argument("before", before),
          Argument("first", first),
          Argument("last", last),
          Argument("searchTerm", searchTerm)
        )
      )

    /**
      * Indicates if Large File Storage (LFS) is enabled for namespace
      */
    def lfsEnabled: SelectionBuilder[Group, Option[Boolean]] = Field("lfsEnabled", OptionOf(Scalar()))

    /**
      * Indicates if a group is disabled from getting mentioned
      */
    def mentionsDisabled: SelectionBuilder[Group, Option[Boolean]] = Field("mentionsDisabled", OptionOf(Scalar()))

    /**
      * Find milestones
      */
    def milestones[A](
      startDate: Option[Time] = None,
      endDate: Option[Time] = None,
      state: Option[MilestoneStateEnum] = None,
      includeDescendants: Option[Boolean] = None,
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[MilestoneConnection, A]
    ): SelectionBuilder[Group, Option[A]] =
      Field(
        "milestones",
        OptionOf(Obj(innerSelection)),
        arguments = List(
          Argument("startDate", startDate),
          Argument("endDate", endDate),
          Argument("state", state),
          Argument("includeDescendants", includeDescendants),
          Argument("after", after),
          Argument("before", before),
          Argument("first", first),
          Argument("last", last)
        )
      )

    /**
      * Name of the namespace
      */
    def name: SelectionBuilder[Group, String] = Field("name", Scalar())

    /**
      * Parent group
      */
    def parent[A](innerSelection: SelectionBuilder[Group, A]): SelectionBuilder[Group, Option[A]] =
      Field("parent", OptionOf(Obj(innerSelection)))

    /**
      * Path of the namespace
      */
    def path: SelectionBuilder[Group, String] = Field("path", Scalar())

    /**
      * The permission level required to create projects in the group
      */
    def projectCreationLevel: SelectionBuilder[Group, Option[String]] = Field("projectCreationLevel", OptionOf(Scalar()))

    /**
      * Projects within this namespace
      */
    def projects[A](
      includeSubgroups: Option[Boolean] = None,
      hasVulnerabilities: Option[Boolean] = None,
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[ProjectConnection, A]
    ): SelectionBuilder[Group, A] =
      Field(
        "projects",
        Obj(innerSelection),
        arguments = List(
          Argument("includeSubgroups", includeSubgroups),
          Argument("hasVulnerabilities", hasVulnerabilities),
          Argument("after", after),
          Argument("before", before),
          Argument("first", first),
          Argument("last", last)
        )
      )

    /**
      * Indicates if users can request access to namespace
      */
    def requestAccessEnabled: SelectionBuilder[Group, Option[Boolean]] = Field("requestAccessEnabled", OptionOf(Scalar()))

    /**
      * Indicates if all users in this group are required to set up two-factor authentication
      */
    def requireTwoFactorAuthentication: SelectionBuilder[Group, Option[Boolean]] =
      Field("requireTwoFactorAuthentication", OptionOf(Scalar()))

    /**
      * Aggregated storage statistics of the namespace. Only available for root namespaces
      */
    def rootStorageStatistics[A](innerSelection: SelectionBuilder[RootStorageStatistics, A]): SelectionBuilder[Group, Option[A]] =
      Field("rootStorageStatistics", OptionOf(Obj(innerSelection)))

    /**
      * Indicates if sharing a project with another group within this group is prevented
      */
    def shareWithGroupLock: SelectionBuilder[Group, Option[Boolean]] = Field("shareWithGroupLock", OptionOf(Scalar()))

    /**
      * Total storage limit of the root namespace in bytes
      */
    def storageSizeLimit: SelectionBuilder[Group, Option[Double]] = Field("storageSizeLimit", OptionOf(Scalar()))

    /**
      * The permission level required to create subgroups within the group
      */
    def subgroupCreationLevel: SelectionBuilder[Group, Option[String]] = Field("subgroupCreationLevel", OptionOf(Scalar()))

    /**
      * Date until the temporary storage increase is active
      */
    def temporaryStorageIncreaseEndsOn: SelectionBuilder[Group, Option[Time]] = Field("temporaryStorageIncreaseEndsOn", OptionOf(Scalar()))

    /**
      * Time logged in issues by group members
      */
    def timelogs[A](
      startDate: Option[Time] = None,
      endDate: Option[Time] = None,
      startTime: Option[Time] = None,
      endTime: Option[Time] = None,
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[TimelogConnection, A]
    ): SelectionBuilder[Group, A] =
      Field(
        "timelogs",
        Obj(innerSelection),
        arguments = List(
          Argument("startDate", startDate),
          Argument("endDate", endDate),
          Argument("startTime", startTime),
          Argument("endTime", endTime),
          Argument("after", after),
          Argument("before", before),
          Argument("first", first),
          Argument("last", last)
        )
      )

    /**
      * Time before two-factor authentication is enforced
      */
    def twoFactorGracePeriod: SelectionBuilder[Group, Option[Int]] = Field("twoFactorGracePeriod", OptionOf(Scalar()))

    /**
      * Permissions for the current user on the resource
      */
    def userPermissions[A](innerSelection: SelectionBuilder[GroupPermissions, A]): SelectionBuilder[Group, A] =
      Field("userPermissions", Obj(innerSelection))

    /**
      * Visibility of the namespace
      */
    def visibility: SelectionBuilder[Group, Option[String]] = Field("visibility", OptionOf(Scalar()))

    /**
      * Vulnerabilities reported on the projects in the group and its subgroups
      */
    def vulnerabilities[A](
      projectId: Option[List[String]] = None,
      reportType: Option[List[VulnerabilityReportType]] = None,
      severity: Option[List[VulnerabilitySeverity]] = None,
      state: Option[List[VulnerabilityState]] = None,
      scanner: Option[List[String]] = None,
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[VulnerabilityConnection, A]
    ): SelectionBuilder[Group, Option[A]] =
      Field(
        "vulnerabilities",
        OptionOf(Obj(innerSelection)),
        arguments = List(
          Argument("projectId", projectId),
          Argument("reportType", reportType),
          Argument("severity", severity),
          Argument("state", state),
          Argument("scanner", scanner),
          Argument("after", after),
          Argument("before", before),
          Argument("first", first),
          Argument("last", last)
        )
      )

    /**
      * Number of vulnerabilities per severity level, per day, for the projects in the group and its subgroups
      */
    def vulnerabilitiesCountByDayAndSeverity[A](
      startDate: ISO8601Date,
      endDate: ISO8601Date,
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[VulnerabilitiesCountByDayAndSeverityConnection, A]
    ): SelectionBuilder[Group, Option[A]] =
      Field(
        "vulnerabilitiesCountByDayAndSeverity",
        OptionOf(Obj(innerSelection)),
        arguments = List(
          Argument("startDate", startDate),
          Argument("endDate", endDate),
          Argument("after", after),
          Argument("before", before),
          Argument("first", first),
          Argument("last", last)
        )
      )

    /**
      * Vulnerability scanners reported on the project vulnerabilties of the group and its subgroups
      */
    def vulnerabilityScanners[A](
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[VulnerabilityScannerConnection, A]
    ): SelectionBuilder[Group, Option[A]] =
      Field(
        "vulnerabilityScanners",
        OptionOf(Obj(innerSelection)),
        arguments = List(Argument("after", after), Argument("before", before), Argument("first", first), Argument("last", last))
      )

    /**
      * Web URL of the group
      */
    def webUrl: SelectionBuilder[Group, String] = Field("webUrl", Scalar())
  }

  type GroupMember

  object GroupMember {

    /**
      * GitLab::Access level
      */
    def accessLevel[A](innerSelection: SelectionBuilder[AccessLevel, A]): SelectionBuilder[GroupMember, Option[A]] =
      Field("accessLevel", OptionOf(Obj(innerSelection)))

    /**
      * Date and time the membership was created
      */
    def createdAt: SelectionBuilder[GroupMember, Option[Time]] = Field("createdAt", OptionOf(Scalar()))

    /**
      * User that authorized membership
      */
    def createdBy[A](innerSelection: SelectionBuilder[User, A]): SelectionBuilder[GroupMember, Option[A]] =
      Field("createdBy", OptionOf(Obj(innerSelection)))

    /**
      * Date and time the membership expires
      */
    def expiresAt: SelectionBuilder[GroupMember, Option[Time]] = Field("expiresAt", OptionOf(Scalar()))

    /**
      * Group that a User is a member of
      */
    def group[A](innerSelection: SelectionBuilder[Group, A]): SelectionBuilder[GroupMember, Option[A]] =
      Field("group", OptionOf(Obj(innerSelection)))

    /**
      * Date and time the membership was last updated
      */
    def updatedAt: SelectionBuilder[GroupMember, Option[Time]] = Field("updatedAt", OptionOf(Scalar()))

    /**
      * Permissions for the current user on the resource
      */
    def userPermissions[A](innerSelection: SelectionBuilder[GroupPermissions, A]): SelectionBuilder[GroupMember, A] =
      Field("userPermissions", Obj(innerSelection))
  }

  type GroupMemberConnection

  object GroupMemberConnection {

    /**
      * A list of edges.
      */
    def edges[A](innerSelection: SelectionBuilder[GroupMemberEdge, A]): SelectionBuilder[GroupMemberConnection, Option[List[Option[A]]]] =
      Field("edges", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * A list of nodes.
      */
    def nodes[A](innerSelection: SelectionBuilder[GroupMember, A]): SelectionBuilder[GroupMemberConnection, Option[List[Option[A]]]] =
      Field("nodes", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * Information to aid in pagination.
      */
    def pageInfo[A](innerSelection: SelectionBuilder[PageInfo, A]): SelectionBuilder[GroupMemberConnection, A] =
      Field("pageInfo", Obj(innerSelection))
  }

  type GroupMemberEdge

  object GroupMemberEdge {

    /**
      * A cursor for use in pagination.
      */
    def cursor: SelectionBuilder[GroupMemberEdge, String] = Field("cursor", Scalar())

    /**
      * The item at the end of the edge.
      */
    def node[A](innerSelection: SelectionBuilder[GroupMember, A]): SelectionBuilder[GroupMemberEdge, Option[A]] =
      Field("node", OptionOf(Obj(innerSelection)))
  }

  type GroupPermissions

  object GroupPermissions {

    /**
      * Indicates the user can perform `read_group` on this resource
      */
    def readGroup: SelectionBuilder[GroupPermissions, Boolean] = Field("readGroup", Scalar())
  }

  type InstanceSecurityDashboard

  object InstanceSecurityDashboard {

    /**
      * Projects selected in Instance Security Dashboard
      */
    def projects[A](
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[ProjectConnection, A]
    ): SelectionBuilder[InstanceSecurityDashboard, A] =
      Field(
        "projects",
        Obj(innerSelection),
        arguments = List(Argument("after", after), Argument("before", before), Argument("first", first), Argument("last", last))
      )

    /**
      * Vulnerability scanners reported on the vulnerabilties from projects selected in Instance Security Dashboard
      */
    def vulnerabilityScanners[A](
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[VulnerabilityScannerConnection, A]
    ): SelectionBuilder[InstanceSecurityDashboard, Option[A]] =
      Field(
        "vulnerabilityScanners",
        OptionOf(Obj(innerSelection)),
        arguments = List(Argument("after", after), Argument("before", before), Argument("first", first), Argument("last", last))
      )

  }

  type Issue

  object Issue {

    /**
      * Assignees of the issue
      */
    def assignees[A](
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[UserConnection, A]
    ): SelectionBuilder[Issue, Option[A]] =
      Field(
        "assignees",
        OptionOf(Obj(innerSelection)),
        arguments = List(Argument("after", after), Argument("before", before), Argument("first", first), Argument("last", last))
      )

    /**
      * User that created the issue
      */
    def author[A](innerSelection: SelectionBuilder[User, A]): SelectionBuilder[Issue, A] = Field("author", Obj(innerSelection))

    /**
      * Indicates the issue is blocked
      */
    def blocked: SelectionBuilder[Issue, Boolean] = Field("blocked", Scalar())

    /**
      * Timestamp of when the issue was closed
      */
    def closedAt: SelectionBuilder[Issue, Option[Time]] = Field("closedAt", OptionOf(Scalar()))

    /**
      * Indicates the issue is confidential
      */
    def confidential: SelectionBuilder[Issue, Boolean] = Field("confidential", Scalar())

    /**
      * Timestamp of when the issue was created
      */
    def createdAt: SelectionBuilder[Issue, Time] = Field("createdAt", Scalar())

    /**
      * Description of the issue
      */
    def description: SelectionBuilder[Issue, Option[String]] = Field("description", OptionOf(Scalar()))

    /**
      * The GitLab Flavored Markdown rendering of `description`
      */
    def descriptionHtml: SelectionBuilder[Issue, Option[String]] = Field("descriptionHtml", OptionOf(Scalar()))

    /**
      * Collection of design images associated with this issue
      */
    def designCollection[A](innerSelection: SelectionBuilder[DesignCollection, A]): SelectionBuilder[Issue, Option[A]] =
      Field("designCollection", OptionOf(Obj(innerSelection)))

    /**
      * The designs associated with this issue. Deprecated in 12.2: Use `designCollection`
      */
    @deprecated("Use `designCollection`. Deprecated in 12.2", "")
    def designs[A](innerSelection: SelectionBuilder[DesignCollection, A]): SelectionBuilder[Issue, Option[A]] =
      Field("designs", OptionOf(Obj(innerSelection)))

    /**
      * Indicates discussion is locked on the issue
      */
    def discussionLocked: SelectionBuilder[Issue, Boolean] = Field("discussionLocked", Scalar())

    /**
      * All discussions on this noteable
      */
    def discussions[A](
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[DiscussionConnection, A]
    ): SelectionBuilder[Issue, A] =
      Field(
        "discussions",
        Obj(innerSelection),
        arguments = List(Argument("after", after), Argument("before", before), Argument("first", first), Argument("last", last))
      )

    /**
      * Number of downvotes the issue has received
      */
    def downvotes: SelectionBuilder[Issue, Int] = Field("downvotes", Scalar())

    /**
      * Due date of the issue
      */
    def dueDate: SelectionBuilder[Issue, Option[Time]] = Field("dueDate", OptionOf(Scalar()))

    /**
      * Epic to which this issue belongs
      */
    def epic[A](innerSelection: SelectionBuilder[Epic, A]): SelectionBuilder[Issue, Option[A]] =
      Field("epic", OptionOf(Obj(innerSelection)))

    /**
      * Current health status. Returns null if `save_issuable_health_status` feature flag is disabled.
      */
    def healthStatus: SelectionBuilder[Issue, Option[HealthStatus]] = Field("healthStatus", OptionOf(Scalar()))

    /**
      * ID of the issue
      */
    def id: SelectionBuilder[Issue, String] = Field("id", Scalar())

    /**
      * Internal ID of the issue
      */
    def iid: SelectionBuilder[Issue, String] = Field("iid", Scalar())

    /**
      * Iteration of the issue
      */
    def iteration[A](innerSelection: SelectionBuilder[Iteration, A]): SelectionBuilder[Issue, Option[A]] =
      Field("iteration", OptionOf(Obj(innerSelection)))

    /**
      * Labels of the issue
      */
    def labels[A](
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[LabelConnection, A]
    ): SelectionBuilder[Issue, Option[A]] =
      Field(
        "labels",
        OptionOf(Obj(innerSelection)),
        arguments = List(Argument("after", after), Argument("before", before), Argument("first", first), Argument("last", last))
      )

    /**
      * Milestone of the issue
      */
    def milestone[A](innerSelection: SelectionBuilder[Milestone, A]): SelectionBuilder[Issue, Option[A]] =
      Field("milestone", OptionOf(Obj(innerSelection)))

    /**
      * All notes on this noteable
      */
    def notes[A](
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[NoteConnection, A]
    ): SelectionBuilder[Issue, A] =
      Field(
        "notes",
        Obj(innerSelection),
        arguments = List(Argument("after", after), Argument("before", before), Argument("first", first), Argument("last", last))
      )

    /**
      * List of participants in the issue
      */
    def participants[A](
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[UserConnection, A]
    ): SelectionBuilder[Issue, Option[A]] =
      Field(
        "participants",
        OptionOf(Obj(innerSelection)),
        arguments = List(Argument("after", after), Argument("before", before), Argument("first", first), Argument("last", last))
      )

    /**
      * Internal reference of the issue. Returned in shortened format by default
      */
    def reference(full: Option[Boolean] = None): SelectionBuilder[Issue, String] =
      Field("reference", Scalar(), arguments = List(Argument("full", full)))

    /**
      * Relative position of the issue (used for positioning in epic tree and issue boards)
      */
    def relativePosition: SelectionBuilder[Issue, Option[Int]] = Field("relativePosition", OptionOf(Scalar()))

    /**
      * State of the issue
      */
    def state: SelectionBuilder[Issue, IssueState] = Field("state", Scalar())

    /**
      * Indicates the currently logged in user is subscribed to the issue
      */
    def subscribed: SelectionBuilder[Issue, Boolean] = Field("subscribed", Scalar())

    /**
      * Task completion status of the issue
      */
    def taskCompletionStatus[A](innerSelection: SelectionBuilder[TaskCompletionStatus, A]): SelectionBuilder[Issue, A] =
      Field("taskCompletionStatus", Obj(innerSelection))

    /**
      * Time estimate of the issue
      */
    def timeEstimate: SelectionBuilder[Issue, Int] = Field("timeEstimate", Scalar())

    /**
      * Title of the issue
      */
    def title: SelectionBuilder[Issue, String] = Field("title", Scalar())

    /**
      * The GitLab Flavored Markdown rendering of `title`
      */
    def titleHtml: SelectionBuilder[Issue, Option[String]] = Field("titleHtml", OptionOf(Scalar()))

    /**
      * Total time reported as spent on the issue
      */
    def totalTimeSpent: SelectionBuilder[Issue, Int] = Field("totalTimeSpent", Scalar())

    /**
      * Timestamp of when the issue was last updated
      */
    def updatedAt: SelectionBuilder[Issue, Time] = Field("updatedAt", Scalar())

    /**
      * Number of upvotes the issue has received
      */
    def upvotes: SelectionBuilder[Issue, Int] = Field("upvotes", Scalar())

    /**
      * Number of user notes of the issue
      */
    def userNotesCount: SelectionBuilder[Issue, Int] = Field("userNotesCount", Scalar())

    /**
      * Permissions for the current user on the resource
      */
    def userPermissions[A](innerSelection: SelectionBuilder[IssuePermissions, A]): SelectionBuilder[Issue, A] =
      Field("userPermissions", Obj(innerSelection))

    /**
      * Web path of the issue
      */
    def webPath: SelectionBuilder[Issue, String] = Field("webPath", Scalar())

    /**
      * Web URL of the issue
      */
    def webUrl: SelectionBuilder[Issue, String] = Field("webUrl", Scalar())

    /**
      * Weight of the issue
      */
    def weight: SelectionBuilder[Issue, Option[Int]] = Field("weight", OptionOf(Scalar()))
  }

  type IssueConnection

  object IssueConnection {

    /**
      * Total count of collection
      */
    def count: SelectionBuilder[IssueConnection, Int] = Field("count", Scalar())

    /**
      * A list of edges.
      */
    def edges[A](innerSelection: SelectionBuilder[IssueEdge, A]): SelectionBuilder[IssueConnection, Option[List[Option[A]]]] =
      Field("edges", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * A list of nodes.
      */
    def nodes[A](innerSelection: SelectionBuilder[Issue, A]): SelectionBuilder[IssueConnection, Option[List[Option[A]]]] =
      Field("nodes", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * Information to aid in pagination.
      */
    def pageInfo[A](innerSelection: SelectionBuilder[PageInfo, A]): SelectionBuilder[IssueConnection, A] =
      Field("pageInfo", Obj(innerSelection))
  }

  type IssueEdge

  object IssueEdge {

    /**
      * A cursor for use in pagination.
      */
    def cursor: SelectionBuilder[IssueEdge, String] = Field("cursor", Scalar())

    /**
      * The item at the end of the edge.
      */
    def node[A](innerSelection: SelectionBuilder[Issue, A]): SelectionBuilder[IssueEdge, Option[A]] =
      Field("node", OptionOf(Obj(innerSelection)))
  }

  type IssuePermissions

  object IssuePermissions {

    /**
      * Indicates the user can perform `admin_issue` on this resource
      */
    def adminIssue: SelectionBuilder[IssuePermissions, Boolean] = Field("adminIssue", Scalar())

    /**
      * Indicates the user can perform `create_design` on this resource
      */
    def createDesign: SelectionBuilder[IssuePermissions, Boolean] = Field("createDesign", Scalar())

    /**
      * Indicates the user can perform `create_note` on this resource
      */
    def createNote: SelectionBuilder[IssuePermissions, Boolean] = Field("createNote", Scalar())

    /**
      * Indicates the user can perform `destroy_design` on this resource
      */
    def destroyDesign: SelectionBuilder[IssuePermissions, Boolean] = Field("destroyDesign", Scalar())

    /**
      * Indicates the user can perform `read_design` on this resource
      */
    def readDesign: SelectionBuilder[IssuePermissions, Boolean] = Field("readDesign", Scalar())

    /**
      * Indicates the user can perform `read_issue` on this resource
      */
    def readIssue: SelectionBuilder[IssuePermissions, Boolean] = Field("readIssue", Scalar())

    /**
      * Indicates the user can perform `reopen_issue` on this resource
      */
    def reopenIssue: SelectionBuilder[IssuePermissions, Boolean] = Field("reopenIssue", Scalar())

    /**
      * Indicates the user can perform `update_issue` on this resource
      */
    def updateIssue: SelectionBuilder[IssuePermissions, Boolean] = Field("updateIssue", Scalar())
  }

  type IssueSetConfidentialPayload

  object IssueSetConfidentialPayload {

    /**
      * A unique identifier for the client performing the mutation.
      */
    def clientMutationId: SelectionBuilder[IssueSetConfidentialPayload, Option[String]] = Field("clientMutationId", OptionOf(Scalar()))

    /**
      * Errors encountered during execution of the mutation.
      */
    def errors: SelectionBuilder[IssueSetConfidentialPayload, List[String]] = Field("errors", ListOf(Scalar()))

    /**
      * The issue after mutation
      */
    def issue[A](innerSelection: SelectionBuilder[Issue, A]): SelectionBuilder[IssueSetConfidentialPayload, Option[A]] =
      Field("issue", OptionOf(Obj(innerSelection)))
  }

  type IssueSetDueDatePayload

  object IssueSetDueDatePayload {

    /**
      * A unique identifier for the client performing the mutation.
      */
    def clientMutationId: SelectionBuilder[IssueSetDueDatePayload, Option[String]] = Field("clientMutationId", OptionOf(Scalar()))

    /**
      * Errors encountered during execution of the mutation.
      */
    def errors: SelectionBuilder[IssueSetDueDatePayload, List[String]] = Field("errors", ListOf(Scalar()))

    /**
      * The issue after mutation
      */
    def issue[A](innerSelection: SelectionBuilder[Issue, A]): SelectionBuilder[IssueSetDueDatePayload, Option[A]] =
      Field("issue", OptionOf(Obj(innerSelection)))
  }

  type IssueSetIterationPayload

  object IssueSetIterationPayload {

    /**
      * A unique identifier for the client performing the mutation.
      */
    def clientMutationId: SelectionBuilder[IssueSetIterationPayload, Option[String]] = Field("clientMutationId", OptionOf(Scalar()))

    /**
      * Errors encountered during execution of the mutation.
      */
    def errors: SelectionBuilder[IssueSetIterationPayload, List[String]] = Field("errors", ListOf(Scalar()))

    /**
      * The issue after mutation
      */
    def issue[A](innerSelection: SelectionBuilder[Issue, A]): SelectionBuilder[IssueSetIterationPayload, Option[A]] =
      Field("issue", OptionOf(Obj(innerSelection)))
  }

  type IssueSetLockedPayload

  object IssueSetLockedPayload {

    /**
      * A unique identifier for the client performing the mutation.
      */
    def clientMutationId: SelectionBuilder[IssueSetLockedPayload, Option[String]] = Field("clientMutationId", OptionOf(Scalar()))

    /**
      * Errors encountered during execution of the mutation.
      */
    def errors: SelectionBuilder[IssueSetLockedPayload, List[String]] = Field("errors", ListOf(Scalar()))

    /**
      * The issue after mutation
      */
    def issue[A](innerSelection: SelectionBuilder[Issue, A]): SelectionBuilder[IssueSetLockedPayload, Option[A]] =
      Field("issue", OptionOf(Obj(innerSelection)))
  }

  type IssueSetWeightPayload

  object IssueSetWeightPayload {

    /**
      * A unique identifier for the client performing the mutation.
      */
    def clientMutationId: SelectionBuilder[IssueSetWeightPayload, Option[String]] = Field("clientMutationId", OptionOf(Scalar()))

    /**
      * Errors encountered during execution of the mutation.
      */
    def errors: SelectionBuilder[IssueSetWeightPayload, List[String]] = Field("errors", ListOf(Scalar()))

    /**
      * The issue after mutation
      */
    def issue[A](innerSelection: SelectionBuilder[Issue, A]): SelectionBuilder[IssueSetWeightPayload, Option[A]] =
      Field("issue", OptionOf(Obj(innerSelection)))
  }

  type Iteration

  object Iteration {

    /**
      * Timestamp of iteration creation
      */
    def createdAt: SelectionBuilder[Iteration, Time] = Field("createdAt", Scalar())

    /**
      * Description of the iteration
      */
    def description: SelectionBuilder[Iteration, Option[String]] = Field("description", OptionOf(Scalar()))

    /**
      * Timestamp of the iteration due date
      */
    def dueDate: SelectionBuilder[Iteration, Option[Time]] = Field("dueDate", OptionOf(Scalar()))

    /**
      * ID of the iteration
      */
    def id: SelectionBuilder[Iteration, String] = Field("id", Scalar())

    /**
      * Internal ID of the iteration
      */
    def iid: SelectionBuilder[Iteration, String] = Field("iid", Scalar())

    /**
      * Timestamp of the iteration start date
      */
    def startDate: SelectionBuilder[Iteration, Option[Time]] = Field("startDate", OptionOf(Scalar()))

    /**
      * State of the iteration
      */
    def state: SelectionBuilder[Iteration, IterationState] = Field("state", Scalar())

    /**
      * Title of the iteration
      */
    def title: SelectionBuilder[Iteration, String] = Field("title", Scalar())

    /**
      * Timestamp of last iteration update
      */
    def updatedAt: SelectionBuilder[Iteration, Time] = Field("updatedAt", Scalar())

    /**
      * Web path of the iteration
      */
    def webPath: SelectionBuilder[Iteration, String] = Field("webPath", Scalar())

    /**
      * Web URL of the iteration
      */
    def webUrl: SelectionBuilder[Iteration, String] = Field("webUrl", Scalar())
  }

  type IterationConnection

  object IterationConnection {

    /**
      * A list of edges.
      */
    def edges[A](innerSelection: SelectionBuilder[IterationEdge, A]): SelectionBuilder[IterationConnection, Option[List[Option[A]]]] =
      Field("edges", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * A list of nodes.
      */
    def nodes[A](innerSelection: SelectionBuilder[Iteration, A]): SelectionBuilder[IterationConnection, Option[List[Option[A]]]] =
      Field("nodes", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * Information to aid in pagination.
      */
    def pageInfo[A](innerSelection: SelectionBuilder[PageInfo, A]): SelectionBuilder[IterationConnection, A] =
      Field("pageInfo", Obj(innerSelection))
  }

  type IterationEdge

  object IterationEdge {

    /**
      * A cursor for use in pagination.
      */
    def cursor: SelectionBuilder[IterationEdge, String] = Field("cursor", Scalar())

    /**
      * The item at the end of the edge.
      */
    def node[A](innerSelection: SelectionBuilder[Iteration, A]): SelectionBuilder[IterationEdge, Option[A]] =
      Field("node", OptionOf(Obj(innerSelection)))
  }

  type JiraImport

  object JiraImport {

    /**
      * Timestamp of when the Jira import was created
      */
    def createdAt: SelectionBuilder[JiraImport, Option[Time]] = Field("createdAt", OptionOf(Scalar()))

    /**
      * Count of issues that failed to import
      */
    def failedToImportCount: SelectionBuilder[JiraImport, Int] = Field("failedToImportCount", Scalar())

    /**
      * Count of issues that were successfully imported
      */
    def importedIssuesCount: SelectionBuilder[JiraImport, Int] = Field("importedIssuesCount", Scalar())

    /**
      * Project key for the imported Jira project
      */
    def jiraProjectKey: SelectionBuilder[JiraImport, String] = Field("jiraProjectKey", Scalar())

    /**
      * Timestamp of when the Jira import was scheduled
      */
    def scheduledAt: SelectionBuilder[JiraImport, Option[Time]] = Field("scheduledAt", OptionOf(Scalar()))

    /**
      * User that started the Jira import
      */
    def scheduledBy[A](innerSelection: SelectionBuilder[User, A]): SelectionBuilder[JiraImport, Option[A]] =
      Field("scheduledBy", OptionOf(Obj(innerSelection)))

    /**
      * Total count of issues that were attempted to import
      */
    def totalIssueCount: SelectionBuilder[JiraImport, Int] = Field("totalIssueCount", Scalar())
  }

  type JiraImportConnection

  object JiraImportConnection {

    /**
      * A list of edges.
      */
    def edges[A](innerSelection: SelectionBuilder[JiraImportEdge, A]): SelectionBuilder[JiraImportConnection, Option[List[Option[A]]]] =
      Field("edges", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * A list of nodes.
      */
    def nodes[A](innerSelection: SelectionBuilder[JiraImport, A]): SelectionBuilder[JiraImportConnection, Option[List[Option[A]]]] =
      Field("nodes", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * Information to aid in pagination.
      */
    def pageInfo[A](innerSelection: SelectionBuilder[PageInfo, A]): SelectionBuilder[JiraImportConnection, A] =
      Field("pageInfo", Obj(innerSelection))
  }

  type JiraImportEdge

  object JiraImportEdge {

    /**
      * A cursor for use in pagination.
      */
    def cursor: SelectionBuilder[JiraImportEdge, String] = Field("cursor", Scalar())

    /**
      * The item at the end of the edge.
      */
    def node[A](innerSelection: SelectionBuilder[JiraImport, A]): SelectionBuilder[JiraImportEdge, Option[A]] =
      Field("node", OptionOf(Obj(innerSelection)))
  }

  type JiraImportStartPayload

  object JiraImportStartPayload {

    /**
      * A unique identifier for the client performing the mutation.
      */
    def clientMutationId: SelectionBuilder[JiraImportStartPayload, Option[String]] = Field("clientMutationId", OptionOf(Scalar()))

    /**
      * Errors encountered during execution of the mutation.
      */
    def errors: SelectionBuilder[JiraImportStartPayload, List[String]] = Field("errors", ListOf(Scalar()))

    /**
      * The Jira import data after mutation
      */
    def jiraImport[A](innerSelection: SelectionBuilder[JiraImport, A]): SelectionBuilder[JiraImportStartPayload, Option[A]] =
      Field("jiraImport", OptionOf(Obj(innerSelection)))
  }

  type JiraImportUsersPayload

  object JiraImportUsersPayload {

    /**
      * A unique identifier for the client performing the mutation.
      */
    def clientMutationId: SelectionBuilder[JiraImportUsersPayload, Option[String]] = Field("clientMutationId", OptionOf(Scalar()))

    /**
      * Errors encountered during execution of the mutation.
      */
    def errors: SelectionBuilder[JiraImportUsersPayload, List[String]] = Field("errors", ListOf(Scalar()))

    /**
      * Users returned from Jira, matched by email and name if possible.
      */
    def jiraUsers[A](innerSelection: SelectionBuilder[JiraUser, A]): SelectionBuilder[JiraImportUsersPayload, Option[List[A]]] =
      Field("jiraUsers", OptionOf(ListOf(Obj(innerSelection))))
  }

  type JiraProject

  object JiraProject {

    /**
      * Key of the Jira project
      */
    def key: SelectionBuilder[JiraProject, String] = Field("key", Scalar())

    /**
      * Name of the Jira project
      */
    def name: SelectionBuilder[JiraProject, Option[String]] = Field("name", OptionOf(Scalar()))

    /**
      * ID of the Jira project
      */
    def projectId: SelectionBuilder[JiraProject, Int] = Field("projectId", Scalar())
  }

  type JiraProjectConnection

  object JiraProjectConnection {

    /**
      * A list of edges.
      */
    def edges[A](innerSelection: SelectionBuilder[JiraProjectEdge, A]): SelectionBuilder[JiraProjectConnection, Option[List[Option[A]]]] =
      Field("edges", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * A list of nodes.
      */
    def nodes[A](innerSelection: SelectionBuilder[JiraProject, A]): SelectionBuilder[JiraProjectConnection, Option[List[Option[A]]]] =
      Field("nodes", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * Information to aid in pagination.
      */
    def pageInfo[A](innerSelection: SelectionBuilder[PageInfo, A]): SelectionBuilder[JiraProjectConnection, A] =
      Field("pageInfo", Obj(innerSelection))
  }

  type JiraProjectEdge

  object JiraProjectEdge {

    /**
      * A cursor for use in pagination.
      */
    def cursor: SelectionBuilder[JiraProjectEdge, String] = Field("cursor", Scalar())

    /**
      * The item at the end of the edge.
      */
    def node[A](innerSelection: SelectionBuilder[JiraProject, A]): SelectionBuilder[JiraProjectEdge, Option[A]] =
      Field("node", OptionOf(Obj(innerSelection)))
  }

  type JiraService

  object JiraService {

    /**
      * Indicates if the service is active
      */
    def active: SelectionBuilder[JiraService, Option[Boolean]] = Field("active", OptionOf(Scalar()))

    /**
      * List of all Jira projects fetched through Jira REST API
      */
    def projects[A](
      name: Option[String] = None,
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[JiraProjectConnection, A]
    ): SelectionBuilder[JiraService, Option[A]] =
      Field(
        "projects",
        OptionOf(Obj(innerSelection)),
        arguments = List(
          Argument("name", name),
          Argument("after", after),
          Argument("before", before),
          Argument("first", first),
          Argument("last", last)
        )
      )

    /**
      * Class name of the service
      */
    def `type`: SelectionBuilder[JiraService, Option[String]] = Field("type", OptionOf(Scalar()))
  }

  type JiraUser

  object JiraUser {

    /**
      * ID of the matched GitLab user
      */
    def gitlabId: SelectionBuilder[JiraUser, Option[Int]] = Field("gitlabId", OptionOf(Scalar()))

    /**
      * Name of the matched GitLab user
      */
    def gitlabName: SelectionBuilder[JiraUser, Option[String]] = Field("gitlabName", OptionOf(Scalar()))

    /**
      * Username of the matched GitLab user
      */
    def gitlabUsername: SelectionBuilder[JiraUser, Option[String]] = Field("gitlabUsername", OptionOf(Scalar()))

    /**
      * Account id of the Jira user
      */
    def jiraAccountId: SelectionBuilder[JiraUser, String] = Field("jiraAccountId", Scalar())

    /**
      * Display name of the Jira user
      */
    def jiraDisplayName: SelectionBuilder[JiraUser, String] = Field("jiraDisplayName", Scalar())

    /**
      * Email of the Jira user, returned only for users with public emails
      */
    def jiraEmail: SelectionBuilder[JiraUser, Option[String]] = Field("jiraEmail", OptionOf(Scalar()))
  }

  type Label

  object Label {

    /**
      * Background color of the label
      */
    def color: SelectionBuilder[Label, String] = Field("color", Scalar())

    /**
      * Description of the label (Markdown rendered as HTML for caching)
      */
    def description: SelectionBuilder[Label, Option[String]] = Field("description", OptionOf(Scalar()))

    /**
      * The GitLab Flavored Markdown rendering of `description`
      */
    def descriptionHtml: SelectionBuilder[Label, Option[String]] = Field("descriptionHtml", OptionOf(Scalar()))

    /**
      * Label ID
      */
    def id: SelectionBuilder[Label, String] = Field("id", Scalar())

    /**
      * Text color of the label
      */
    def textColor: SelectionBuilder[Label, String] = Field("textColor", Scalar())

    /**
      * Content of the label
      */
    def title: SelectionBuilder[Label, String] = Field("title", Scalar())
  }

  type LabelConnection

  object LabelConnection {

    /**
      * A list of edges.
      */
    def edges[A](innerSelection: SelectionBuilder[LabelEdge, A]): SelectionBuilder[LabelConnection, Option[List[Option[A]]]] =
      Field("edges", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * A list of nodes.
      */
    def nodes[A](innerSelection: SelectionBuilder[Label, A]): SelectionBuilder[LabelConnection, Option[List[Option[A]]]] =
      Field("nodes", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * Information to aid in pagination.
      */
    def pageInfo[A](innerSelection: SelectionBuilder[PageInfo, A]): SelectionBuilder[LabelConnection, A] =
      Field("pageInfo", Obj(innerSelection))
  }

  type LabelEdge

  object LabelEdge {

    /**
      * A cursor for use in pagination.
      */
    def cursor: SelectionBuilder[LabelEdge, String] = Field("cursor", Scalar())

    /**
      * The item at the end of the edge.
      */
    def node[A](innerSelection: SelectionBuilder[Label, A]): SelectionBuilder[LabelEdge, Option[A]] =
      Field("node", OptionOf(Obj(innerSelection)))
  }

  type MarkAsSpamSnippetPayload

  object MarkAsSpamSnippetPayload {

    /**
      * A unique identifier for the client performing the mutation.
      */
    def clientMutationId: SelectionBuilder[MarkAsSpamSnippetPayload, Option[String]] = Field("clientMutationId", OptionOf(Scalar()))

    /**
      * Errors encountered during execution of the mutation.
      */
    def errors: SelectionBuilder[MarkAsSpamSnippetPayload, List[String]] = Field("errors", ListOf(Scalar()))

    /**
      * The snippet after mutation
      */
    def snippet[A](innerSelection: SelectionBuilder[Snippet, A]): SelectionBuilder[MarkAsSpamSnippetPayload, Option[A]] =
      Field("snippet", OptionOf(Obj(innerSelection)))
  }

  type MergeRequest

  object MergeRequest {

    /**
      * Indicates if members of the target project can push to the fork
      */
    def allowCollaboration: SelectionBuilder[MergeRequest, Option[Boolean]] = Field("allowCollaboration", OptionOf(Scalar()))

    /**
      * Assignees of the merge request
      */
    def assignees[A](
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[UserConnection, A]
    ): SelectionBuilder[MergeRequest, Option[A]] =
      Field(
        "assignees",
        OptionOf(Obj(innerSelection)),
        arguments = List(Argument("after", after), Argument("before", before), Argument("first", first), Argument("last", last))
      )

    /**
      * User who created this merge request
      */
    def author[A](innerSelection: SelectionBuilder[User, A]): SelectionBuilder[MergeRequest, Option[A]] =
      Field("author", OptionOf(Obj(innerSelection)))

    /**
      * Timestamp of when the merge request was created
      */
    def createdAt: SelectionBuilder[MergeRequest, Time] = Field("createdAt", Scalar())

    /**
      * Default merge commit message of the merge request
      */
    def defaultMergeCommitMessage: SelectionBuilder[MergeRequest, Option[String]] = Field("defaultMergeCommitMessage", OptionOf(Scalar()))

    /**
      * Description of the merge request (Markdown rendered as HTML for caching)
      */
    def description: SelectionBuilder[MergeRequest, Option[String]] = Field("description", OptionOf(Scalar()))

    /**
      * The GitLab Flavored Markdown rendering of `description`
      */
    def descriptionHtml: SelectionBuilder[MergeRequest, Option[String]] = Field("descriptionHtml", OptionOf(Scalar()))

    /**
      * Diff head SHA of the merge request
      */
    def diffHeadSha: SelectionBuilder[MergeRequest, Option[String]] = Field("diffHeadSha", OptionOf(Scalar()))

    /**
      * References of the base SHA, the head SHA, and the start SHA for this merge request
      */
    def diffRefs[A](innerSelection: SelectionBuilder[DiffRefs, A]): SelectionBuilder[MergeRequest, Option[A]] =
      Field("diffRefs", OptionOf(Obj(innerSelection)))

    /**
      * Details about which files were changed in this merge request
      */
    def diffStats[A](
      path: Option[String] = None
    )(
      innerSelection: SelectionBuilder[DiffStats, A]
    ): SelectionBuilder[MergeRequest, Option[List[A]]] =
      Field("diffStats", OptionOf(ListOf(Obj(innerSelection))), arguments = List(Argument("path", path)))

    /**
      * Summary of which files were changed in this merge request
      */
    def diffStatsSummary[A](innerSelection: SelectionBuilder[DiffStatsSummary, A]): SelectionBuilder[MergeRequest, Option[A]] =
      Field("diffStatsSummary", OptionOf(Obj(innerSelection)))

    /**
      * Indicates if comments on the merge request are locked to members only
      */
    def discussionLocked: SelectionBuilder[MergeRequest, Boolean] = Field("discussionLocked", Scalar())

    /**
      * All discussions on this noteable
      */
    def discussions[A](
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[DiscussionConnection, A]
    ): SelectionBuilder[MergeRequest, A] =
      Field(
        "discussions",
        Obj(innerSelection),
        arguments = List(Argument("after", after), Argument("before", before), Argument("first", first), Argument("last", last))
      )

    /**
      * Number of downvotes for the merge request
      */
    def downvotes: SelectionBuilder[MergeRequest, Int] = Field("downvotes", Scalar())

    /**
      * Indicates if the project settings will lead to source branch deletion after merge
      */
    def forceRemoveSourceBranch: SelectionBuilder[MergeRequest, Option[Boolean]] = Field("forceRemoveSourceBranch", OptionOf(Scalar()))

    /**
      * The pipeline running on the branch HEAD of the merge request
      */
    def headPipeline[A](innerSelection: SelectionBuilder[Pipeline, A]): SelectionBuilder[MergeRequest, Option[A]] =
      Field("headPipeline", OptionOf(Obj(innerSelection)))

    /**
      * ID of the merge request
      */
    def id: SelectionBuilder[MergeRequest, String] = Field("id", Scalar())

    /**
      * Internal ID of the merge request
      */
    def iid: SelectionBuilder[MergeRequest, String] = Field("iid", Scalar())

    /**
      * Commit SHA of the merge request if merge is in progress
      */
    def inProgressMergeCommitSha: SelectionBuilder[MergeRequest, Option[String]] = Field("inProgressMergeCommitSha", OptionOf(Scalar()))

    /**
      * Labels of the merge request
      */
    def labels[A](
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[LabelConnection, A]
    ): SelectionBuilder[MergeRequest, Option[A]] =
      Field(
        "labels",
        OptionOf(Obj(innerSelection)),
        arguments = List(Argument("after", after), Argument("before", before), Argument("first", first), Argument("last", last))
      )

    /**
      * Default merge commit message of the merge request. Deprecated in 11.8: Use `defaultMergeCommitMessage`
      */
    @deprecated("Use `defaultMergeCommitMessage`. Deprecated in 11.8", "")
    def mergeCommitMessage: SelectionBuilder[MergeRequest, Option[String]] = Field("mergeCommitMessage", OptionOf(Scalar()))

    /**
      * SHA of the merge request commit (set once merged)
      */
    def mergeCommitSha: SelectionBuilder[MergeRequest, Option[String]] = Field("mergeCommitSha", OptionOf(Scalar()))

    /**
      * Error message due to a merge error
      */
    def mergeError: SelectionBuilder[MergeRequest, Option[String]] = Field("mergeError", OptionOf(Scalar()))

    /**
      * Indicates if a merge is currently occurring
      */
    def mergeOngoing: SelectionBuilder[MergeRequest, Boolean] = Field("mergeOngoing", Scalar())

    /**
      * Status of the merge request
      */
    def mergeStatus: SelectionBuilder[MergeRequest, Option[String]] = Field("mergeStatus", OptionOf(Scalar()))

    /**
      * Indicates if the merge has been set to be merged when its pipeline succeeds (MWPS)
      */
    def mergeWhenPipelineSucceeds: SelectionBuilder[MergeRequest, Option[Boolean]] = Field("mergeWhenPipelineSucceeds", OptionOf(Scalar()))

    /**
      * Indicates if all discussions in the merge request have been resolved, allowing the merge request to be merged
      */
    def mergeableDiscussionsState: SelectionBuilder[MergeRequest, Option[Boolean]] = Field("mergeableDiscussionsState", OptionOf(Scalar()))

    /**
      * Timestamp of when the merge request was merged, null if not merged
      */
    def mergedAt: SelectionBuilder[MergeRequest, Option[Time]] = Field("mergedAt", OptionOf(Scalar()))

    /**
      * The milestone of the merge request
      */
    def milestone[A](innerSelection: SelectionBuilder[Milestone, A]): SelectionBuilder[MergeRequest, Option[A]] =
      Field("milestone", OptionOf(Obj(innerSelection)))

    /**
      * All notes on this noteable
      */
    def notes[A](
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[NoteConnection, A]
    ): SelectionBuilder[MergeRequest, A] =
      Field(
        "notes",
        Obj(innerSelection),
        arguments = List(Argument("after", after), Argument("before", before), Argument("first", first), Argument("last", last))
      )

    /**
      * Participants in the merge request
      */
    def participants[A](
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[UserConnection, A]
    ): SelectionBuilder[MergeRequest, Option[A]] =
      Field(
        "participants",
        OptionOf(Obj(innerSelection)),
        arguments = List(Argument("after", after), Argument("before", before), Argument("first", first), Argument("last", last))
      )

    /**
      * Pipelines for the merge request
      */
    def pipelines[A](
      status: Option[PipelineStatusEnum] = None,
      ref: Option[String] = None,
      sha: Option[String] = None,
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[PipelineConnection, A]
    ): SelectionBuilder[MergeRequest, A] =
      Field(
        "pipelines",
        Obj(innerSelection),
        arguments = List(
          Argument("status", status),
          Argument("ref", ref),
          Argument("sha", sha),
          Argument("after", after),
          Argument("before", before),
          Argument("first", first),
          Argument("last", last)
        )
      )

    /**
      * Alias for target_project
      */
    def project[A](innerSelection: SelectionBuilder[Project, A]): SelectionBuilder[MergeRequest, A] = Field("project", Obj(innerSelection))

    /**
      * ID of the merge request project
      */
    def projectId: SelectionBuilder[MergeRequest, Int] = Field("projectId", Scalar())

    /**
      * Rebase commit SHA of the merge request
      */
    def rebaseCommitSha: SelectionBuilder[MergeRequest, Option[String]] = Field("rebaseCommitSha", OptionOf(Scalar()))

    /**
      * Indicates if there is a rebase currently in progress for the merge request
      */
    def rebaseInProgress: SelectionBuilder[MergeRequest, Boolean] = Field("rebaseInProgress", Scalar())

    /**
      * Internal reference of the merge request. Returned in shortened format by default
      */
    def reference(full: Option[Boolean] = None): SelectionBuilder[MergeRequest, String] =
      Field("reference", Scalar(), arguments = List(Argument("full", full)))

    /**
      * Indicates if the merge request will be rebased
      */
    def shouldBeRebased: SelectionBuilder[MergeRequest, Boolean] = Field("shouldBeRebased", Scalar())

    /**
      * Indicates if the source branch of the merge request will be deleted after merge
      */
    def shouldRemoveSourceBranch: SelectionBuilder[MergeRequest, Option[Boolean]] = Field("shouldRemoveSourceBranch", OptionOf(Scalar()))

    /**
      * Source branch of the merge request
      */
    def sourceBranch: SelectionBuilder[MergeRequest, String] = Field("sourceBranch", Scalar())

    /**
      * Indicates if the source branch of the merge request exists
      */
    def sourceBranchExists: SelectionBuilder[MergeRequest, Boolean] = Field("sourceBranchExists", Scalar())

    /**
      * Source project of the merge request
      */
    def sourceProject[A](innerSelection: SelectionBuilder[Project, A]): SelectionBuilder[MergeRequest, Option[A]] =
      Field("sourceProject", OptionOf(Obj(innerSelection)))

    /**
      * ID of the merge request source project
      */
    def sourceProjectId: SelectionBuilder[MergeRequest, Option[Int]] = Field("sourceProjectId", OptionOf(Scalar()))

    /**
      * State of the merge request
      */
    def state: SelectionBuilder[MergeRequest, MergeRequestState] = Field("state", Scalar())

    /**
      * Indicates if the currently logged in user is subscribed to this merge request
      */
    def subscribed: SelectionBuilder[MergeRequest, Boolean] = Field("subscribed", Scalar())

    /**
      * Target branch of the merge request
      */
    def targetBranch: SelectionBuilder[MergeRequest, String] = Field("targetBranch", Scalar())

    /**
      * Indicates if the target branch of the merge request exists
      */
    def targetBranchExists: SelectionBuilder[MergeRequest, Boolean] = Field("targetBranchExists", Scalar())

    /**
      * Target project of the merge request
      */
    def targetProject[A](innerSelection: SelectionBuilder[Project, A]): SelectionBuilder[MergeRequest, A] =
      Field("targetProject", Obj(innerSelection))

    /**
      * ID of the merge request target project
      */
    def targetProjectId: SelectionBuilder[MergeRequest, Int] = Field("targetProjectId", Scalar())

    /**
      * Completion status of tasks
      */
    def taskCompletionStatus[A](innerSelection: SelectionBuilder[TaskCompletionStatus, A]): SelectionBuilder[MergeRequest, A] =
      Field("taskCompletionStatus", Obj(innerSelection))

    /**
      * Time estimate of the merge request
      */
    def timeEstimate: SelectionBuilder[MergeRequest, Int] = Field("timeEstimate", Scalar())

    /**
      * Title of the merge request
      */
    def title: SelectionBuilder[MergeRequest, String] = Field("title", Scalar())

    /**
      * The GitLab Flavored Markdown rendering of `title`
      */
    def titleHtml: SelectionBuilder[MergeRequest, Option[String]] = Field("titleHtml", OptionOf(Scalar()))

    /**
      * Total time reported as spent on the merge request
      */
    def totalTimeSpent: SelectionBuilder[MergeRequest, Int] = Field("totalTimeSpent", Scalar())

    /**
      * Timestamp of when the merge request was last updated
      */
    def updatedAt: SelectionBuilder[MergeRequest, Time] = Field("updatedAt", Scalar())

    /**
      * Number of upvotes for the merge request
      */
    def upvotes: SelectionBuilder[MergeRequest, Int] = Field("upvotes", Scalar())

    /**
      * User notes count of the merge request
      */
    def userNotesCount: SelectionBuilder[MergeRequest, Option[Int]] = Field("userNotesCount", OptionOf(Scalar()))

    /**
      * Permissions for the current user on the resource
      */
    def userPermissions[A](innerSelection: SelectionBuilder[MergeRequestPermissions, A]): SelectionBuilder[MergeRequest, A] =
      Field("userPermissions", Obj(innerSelection))

    /**
      * Web URL of the merge request
      */
    def webUrl: SelectionBuilder[MergeRequest, Option[String]] = Field("webUrl", OptionOf(Scalar()))

    /**
      * Indicates if the merge request is a work in progress (WIP)
      */
    def workInProgress: SelectionBuilder[MergeRequest, Boolean] = Field("workInProgress", Scalar())
  }

  type MergeRequestConnection

  object MergeRequestConnection {

    /**
      * A list of edges.
      */
    def edges[A](innerSelection: SelectionBuilder[MergeRequestEdge, A]): SelectionBuilder[MergeRequestConnection, Option[List[Option[A]]]] =
      Field("edges", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * A list of nodes.
      */
    def nodes[A](innerSelection: SelectionBuilder[MergeRequest, A]): SelectionBuilder[MergeRequestConnection, Option[List[Option[A]]]] =
      Field("nodes", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * Information to aid in pagination.
      */
    def pageInfo[A](innerSelection: SelectionBuilder[PageInfo, A]): SelectionBuilder[MergeRequestConnection, A] =
      Field("pageInfo", Obj(innerSelection))
  }

  type MergeRequestCreatePayload

  object MergeRequestCreatePayload {

    /**
      * A unique identifier for the client performing the mutation.
      */
    def clientMutationId: SelectionBuilder[MergeRequestCreatePayload, Option[String]] = Field("clientMutationId", OptionOf(Scalar()))

    /**
      * Errors encountered during execution of the mutation.
      */
    def errors: SelectionBuilder[MergeRequestCreatePayload, List[String]] = Field("errors", ListOf(Scalar()))

    /**
      * The merge request after mutation
      */
    def mergeRequest[A](innerSelection: SelectionBuilder[MergeRequest, A]): SelectionBuilder[MergeRequestCreatePayload, Option[A]] =
      Field("mergeRequest", OptionOf(Obj(innerSelection)))
  }

  type MergeRequestEdge

  object MergeRequestEdge {

    /**
      * A cursor for use in pagination.
      */
    def cursor: SelectionBuilder[MergeRequestEdge, String] = Field("cursor", Scalar())

    /**
      * The item at the end of the edge.
      */
    def node[A](innerSelection: SelectionBuilder[MergeRequest, A]): SelectionBuilder[MergeRequestEdge, Option[A]] =
      Field("node", OptionOf(Obj(innerSelection)))
  }

  type MergeRequestPermissions

  object MergeRequestPermissions {

    /**
      * Indicates the user can perform `admin_merge_request` on this resource
      */
    def adminMergeRequest: SelectionBuilder[MergeRequestPermissions, Boolean] = Field("adminMergeRequest", Scalar())

    /**
      * Indicates the user can perform `cherry_pick_on_current_merge_request` on this resource
      */
    def cherryPickOnCurrentMergeRequest: SelectionBuilder[MergeRequestPermissions, Boolean] =
      Field("cherryPickOnCurrentMergeRequest", Scalar())

    /**
      * Indicates the user can perform `create_note` on this resource
      */
    def createNote: SelectionBuilder[MergeRequestPermissions, Boolean] = Field("createNote", Scalar())

    /**
      * Indicates the user can perform `push_to_source_branch` on this resource
      */
    def pushToSourceBranch: SelectionBuilder[MergeRequestPermissions, Boolean] = Field("pushToSourceBranch", Scalar())

    /**
      * Indicates the user can perform `read_merge_request` on this resource
      */
    def readMergeRequest: SelectionBuilder[MergeRequestPermissions, Boolean] = Field("readMergeRequest", Scalar())

    /**
      * Indicates the user can perform `remove_source_branch` on this resource
      */
    def removeSourceBranch: SelectionBuilder[MergeRequestPermissions, Boolean] = Field("removeSourceBranch", Scalar())

    /**
      * Indicates the user can perform `revert_on_current_merge_request` on this resource
      */
    def revertOnCurrentMergeRequest: SelectionBuilder[MergeRequestPermissions, Boolean] = Field("revertOnCurrentMergeRequest", Scalar())

    /**
      * Indicates the user can perform `update_merge_request` on this resource
      */
    def updateMergeRequest: SelectionBuilder[MergeRequestPermissions, Boolean] = Field("updateMergeRequest", Scalar())
  }

  type MergeRequestSetAssigneesPayload

  object MergeRequestSetAssigneesPayload {

    /**
      * A unique identifier for the client performing the mutation.
      */
    def clientMutationId: SelectionBuilder[MergeRequestSetAssigneesPayload, Option[String]] = Field("clientMutationId", OptionOf(Scalar()))

    /**
      * Errors encountered during execution of the mutation.
      */
    def errors: SelectionBuilder[MergeRequestSetAssigneesPayload, List[String]] = Field("errors", ListOf(Scalar()))

    /**
      * The merge request after mutation
      */
    def mergeRequest[A](innerSelection: SelectionBuilder[MergeRequest, A]): SelectionBuilder[MergeRequestSetAssigneesPayload, Option[A]] =
      Field("mergeRequest", OptionOf(Obj(innerSelection)))
  }

  type MergeRequestSetLabelsPayload

  object MergeRequestSetLabelsPayload {

    /**
      * A unique identifier for the client performing the mutation.
      */
    def clientMutationId: SelectionBuilder[MergeRequestSetLabelsPayload, Option[String]] = Field("clientMutationId", OptionOf(Scalar()))

    /**
      * Errors encountered during execution of the mutation.
      */
    def errors: SelectionBuilder[MergeRequestSetLabelsPayload, List[String]] = Field("errors", ListOf(Scalar()))

    /**
      * The merge request after mutation
      */
    def mergeRequest[A](innerSelection: SelectionBuilder[MergeRequest, A]): SelectionBuilder[MergeRequestSetLabelsPayload, Option[A]] =
      Field("mergeRequest", OptionOf(Obj(innerSelection)))
  }

  type MergeRequestSetLockedPayload

  object MergeRequestSetLockedPayload {

    /**
      * A unique identifier for the client performing the mutation.
      */
    def clientMutationId: SelectionBuilder[MergeRequestSetLockedPayload, Option[String]] = Field("clientMutationId", OptionOf(Scalar()))

    /**
      * Errors encountered during execution of the mutation.
      */
    def errors: SelectionBuilder[MergeRequestSetLockedPayload, List[String]] = Field("errors", ListOf(Scalar()))

    /**
      * The merge request after mutation
      */
    def mergeRequest[A](innerSelection: SelectionBuilder[MergeRequest, A]): SelectionBuilder[MergeRequestSetLockedPayload, Option[A]] =
      Field("mergeRequest", OptionOf(Obj(innerSelection)))
  }

  type MergeRequestSetMilestonePayload

  object MergeRequestSetMilestonePayload {

    /**
      * A unique identifier for the client performing the mutation.
      */
    def clientMutationId: SelectionBuilder[MergeRequestSetMilestonePayload, Option[String]] = Field("clientMutationId", OptionOf(Scalar()))

    /**
      * Errors encountered during execution of the mutation.
      */
    def errors: SelectionBuilder[MergeRequestSetMilestonePayload, List[String]] = Field("errors", ListOf(Scalar()))

    /**
      * The merge request after mutation
      */
    def mergeRequest[A](innerSelection: SelectionBuilder[MergeRequest, A]): SelectionBuilder[MergeRequestSetMilestonePayload, Option[A]] =
      Field("mergeRequest", OptionOf(Obj(innerSelection)))
  }

  type MergeRequestSetSubscriptionPayload

  object MergeRequestSetSubscriptionPayload {

    /**
      * A unique identifier for the client performing the mutation.
      */
    def clientMutationId: SelectionBuilder[MergeRequestSetSubscriptionPayload, Option[String]] =
      Field("clientMutationId", OptionOf(Scalar()))

    /**
      * Errors encountered during execution of the mutation.
      */
    def errors: SelectionBuilder[MergeRequestSetSubscriptionPayload, List[String]] = Field("errors", ListOf(Scalar()))

    /**
      * The merge request after mutation
      */
    def mergeRequest[A](
      innerSelection: SelectionBuilder[MergeRequest, A]
    ): SelectionBuilder[MergeRequestSetSubscriptionPayload, Option[A]] = Field("mergeRequest", OptionOf(Obj(innerSelection)))

  }

  type MergeRequestSetWipPayload

  object MergeRequestSetWipPayload {

    /**
      * A unique identifier for the client performing the mutation.
      */
    def clientMutationId: SelectionBuilder[MergeRequestSetWipPayload, Option[String]] = Field("clientMutationId", OptionOf(Scalar()))

    /**
      * Errors encountered during execution of the mutation.
      */
    def errors: SelectionBuilder[MergeRequestSetWipPayload, List[String]] = Field("errors", ListOf(Scalar()))

    /**
      * The merge request after mutation
      */
    def mergeRequest[A](innerSelection: SelectionBuilder[MergeRequest, A]): SelectionBuilder[MergeRequestSetWipPayload, Option[A]] =
      Field("mergeRequest", OptionOf(Obj(innerSelection)))
  }

  type MergeRequestUpdatePayload

  object MergeRequestUpdatePayload {

    /**
      * A unique identifier for the client performing the mutation.
      */
    def clientMutationId: SelectionBuilder[MergeRequestUpdatePayload, Option[String]] = Field("clientMutationId", OptionOf(Scalar()))

    /**
      * Errors encountered during execution of the mutation.
      */
    def errors: SelectionBuilder[MergeRequestUpdatePayload, List[String]] = Field("errors", ListOf(Scalar()))

    /**
      * The merge request after mutation
      */
    def mergeRequest[A](innerSelection: SelectionBuilder[MergeRequest, A]): SelectionBuilder[MergeRequestUpdatePayload, Option[A]] =
      Field("mergeRequest", OptionOf(Obj(innerSelection)))
  }

  type Metadata

  object Metadata {

    /**
      * Revision
      */
    def revision: SelectionBuilder[Metadata, String] = Field("revision", Scalar())

    /**
      * Version
      */
    def version: SelectionBuilder[Metadata, String] = Field("version", Scalar())
  }

  type MetricsDashboard

  object MetricsDashboard {

    /**
      * Annotations added to the dashboard
      */
    def annotations[A](
      from: Time,
      to: Option[Time] = None,
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[MetricsDashboardAnnotationConnection, A]
    ): SelectionBuilder[MetricsDashboard, Option[A]] =
      Field(
        "annotations",
        OptionOf(Obj(innerSelection)),
        arguments = List(
          Argument("from", from),
          Argument("to", to),
          Argument("after", after),
          Argument("before", before),
          Argument("first", first),
          Argument("last", last)
        )
      )

    /**
      * Path to a file with the dashboard definition
      */
    def path: SelectionBuilder[MetricsDashboard, Option[String]] = Field("path", OptionOf(Scalar()))

    /**
      * Dashboard schema validation warnings
      */
    def schemaValidationWarnings: SelectionBuilder[MetricsDashboard, Option[List[String]]] =
      Field("schemaValidationWarnings", OptionOf(ListOf(Scalar())))
  }

  type MetricsDashboardAnnotation

  object MetricsDashboardAnnotation {

    /**
      * Description of the annotation
      */
    def description: SelectionBuilder[MetricsDashboardAnnotation, Option[String]] = Field("description", OptionOf(Scalar()))

    /**
      * Timestamp marking end of annotated time span
      */
    def endingAt: SelectionBuilder[MetricsDashboardAnnotation, Option[Time]] = Field("endingAt", OptionOf(Scalar()))

    /**
      * ID of the annotation
      */
    def id: SelectionBuilder[MetricsDashboardAnnotation, String] = Field("id", Scalar())

    /**
      * ID of a dashboard panel to which the annotation should be scoped
      */
    def panelId: SelectionBuilder[MetricsDashboardAnnotation, Option[String]] = Field("panelId", OptionOf(Scalar()))

    /**
      * Timestamp marking start of annotated time span
      */
    def startingAt: SelectionBuilder[MetricsDashboardAnnotation, Option[Time]] = Field("startingAt", OptionOf(Scalar()))
  }

  type MetricsDashboardAnnotationConnection

  object MetricsDashboardAnnotationConnection {

    /**
      * A list of edges.
      */
    def edges[A](
      innerSelection: SelectionBuilder[MetricsDashboardAnnotationEdge, A]
    ): SelectionBuilder[MetricsDashboardAnnotationConnection, Option[List[Option[A]]]] =
      Field("edges", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * A list of nodes.
      */
    def nodes[A](
      innerSelection: SelectionBuilder[MetricsDashboardAnnotation, A]
    ): SelectionBuilder[MetricsDashboardAnnotationConnection, Option[List[Option[A]]]] =
      Field("nodes", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * Information to aid in pagination.
      */
    def pageInfo[A](innerSelection: SelectionBuilder[PageInfo, A]): SelectionBuilder[MetricsDashboardAnnotationConnection, A] =
      Field("pageInfo", Obj(innerSelection))
  }

  type MetricsDashboardAnnotationEdge

  object MetricsDashboardAnnotationEdge {

    /**
      * A cursor for use in pagination.
      */
    def cursor: SelectionBuilder[MetricsDashboardAnnotationEdge, String] = Field("cursor", Scalar())

    /**
      * The item at the end of the edge.
      */
    def node[A](
      innerSelection: SelectionBuilder[MetricsDashboardAnnotation, A]
    ): SelectionBuilder[MetricsDashboardAnnotationEdge, Option[A]] = Field("node", OptionOf(Obj(innerSelection)))

  }

  type Milestone

  object Milestone {

    /**
      * Timestamp of milestone creation
      */
    def createdAt: SelectionBuilder[Milestone, Time] = Field("createdAt", Scalar())

    /**
      * Description of the milestone
      */
    def description: SelectionBuilder[Milestone, Option[String]] = Field("description", OptionOf(Scalar()))

    /**
      * Timestamp of the milestone due date
      */
    def dueDate: SelectionBuilder[Milestone, Option[Time]] = Field("dueDate", OptionOf(Scalar()))

    /**
      * Indicates if milestone is at group level
      */
    def groupMilestone: SelectionBuilder[Milestone, Boolean] = Field("groupMilestone", Scalar())

    /**
      * ID of the milestone
      */
    def id: SelectionBuilder[Milestone, String] = Field("id", Scalar())

    /**
      * Indicates if milestone is at project level
      */
    def projectMilestone: SelectionBuilder[Milestone, Boolean] = Field("projectMilestone", Scalar())

    /**
      * Timestamp of the milestone start date
      */
    def startDate: SelectionBuilder[Milestone, Option[Time]] = Field("startDate", OptionOf(Scalar()))

    /**
      * State of the milestone
      */
    def state: SelectionBuilder[Milestone, MilestoneStateEnum] = Field("state", Scalar())

    /**
      * Milestone statistics
      */
    def stats[A](innerSelection: SelectionBuilder[MilestoneStats, A]): SelectionBuilder[Milestone, Option[A]] =
      Field("stats", OptionOf(Obj(innerSelection)))

    /**
      * Indicates if milestone is at subgroup level
      */
    def subgroupMilestone: SelectionBuilder[Milestone, Boolean] = Field("subgroupMilestone", Scalar())

    /**
      * Title of the milestone
      */
    def title: SelectionBuilder[Milestone, String] = Field("title", Scalar())

    /**
      * Timestamp of last milestone update
      */
    def updatedAt: SelectionBuilder[Milestone, Time] = Field("updatedAt", Scalar())

    /**
      * Web path of the milestone
      */
    def webPath: SelectionBuilder[Milestone, String] = Field("webPath", Scalar())
  }

  type MilestoneConnection

  object MilestoneConnection {

    /**
      * A list of edges.
      */
    def edges[A](innerSelection: SelectionBuilder[MilestoneEdge, A]): SelectionBuilder[MilestoneConnection, Option[List[Option[A]]]] =
      Field("edges", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * A list of nodes.
      */
    def nodes[A](innerSelection: SelectionBuilder[Milestone, A]): SelectionBuilder[MilestoneConnection, Option[List[Option[A]]]] =
      Field("nodes", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * Information to aid in pagination.
      */
    def pageInfo[A](innerSelection: SelectionBuilder[PageInfo, A]): SelectionBuilder[MilestoneConnection, A] =
      Field("pageInfo", Obj(innerSelection))
  }

  type MilestoneEdge

  object MilestoneEdge {

    /**
      * A cursor for use in pagination.
      */
    def cursor: SelectionBuilder[MilestoneEdge, String] = Field("cursor", Scalar())

    /**
      * The item at the end of the edge.
      */
    def node[A](innerSelection: SelectionBuilder[Milestone, A]): SelectionBuilder[MilestoneEdge, Option[A]] =
      Field("node", OptionOf(Obj(innerSelection)))
  }

  type MilestoneStats

  object MilestoneStats {

    /**
      * Number of closed issues associated with the milestone
      */
    def closedIssuesCount: SelectionBuilder[MilestoneStats, Option[Int]] = Field("closedIssuesCount", OptionOf(Scalar()))

    /**
      * Total number of issues associated with the milestone
      */
    def totalIssuesCount: SelectionBuilder[MilestoneStats, Option[Int]] = Field("totalIssuesCount", OptionOf(Scalar()))
  }

  type Namespace

  object Namespace {

    /**
      * Description of the namespace
      */
    def description: SelectionBuilder[Namespace, Option[String]] = Field("description", OptionOf(Scalar()))

    /**
      * The GitLab Flavored Markdown rendering of `description`
      */
    def descriptionHtml: SelectionBuilder[Namespace, Option[String]] = Field("descriptionHtml", OptionOf(Scalar()))

    /**
      * Full name of the namespace
      */
    def fullName: SelectionBuilder[Namespace, String] = Field("fullName", Scalar())

    /**
      * Full path of the namespace
      */
    def fullPath: SelectionBuilder[Namespace, String] = Field("fullPath", Scalar())

    /**
      * ID of the namespace
      */
    def id: SelectionBuilder[Namespace, String] = Field("id", Scalar())

    /**
      * Status of the temporary storage increase
      */
    def isTemporaryStorageIncreaseEnabled: SelectionBuilder[Namespace, Boolean] = Field("isTemporaryStorageIncreaseEnabled", Scalar())

    /**
      * Indicates if Large File Storage (LFS) is enabled for namespace
      */
    def lfsEnabled: SelectionBuilder[Namespace, Option[Boolean]] = Field("lfsEnabled", OptionOf(Scalar()))

    /**
      * Name of the namespace
      */
    def name: SelectionBuilder[Namespace, String] = Field("name", Scalar())

    /**
      * Path of the namespace
      */
    def path: SelectionBuilder[Namespace, String] = Field("path", Scalar())

    /**
      * Projects within this namespace
      */
    def projects[A](
      includeSubgroups: Option[Boolean] = None,
      hasVulnerabilities: Option[Boolean] = None,
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[ProjectConnection, A]
    ): SelectionBuilder[Namespace, A] =
      Field(
        "projects",
        Obj(innerSelection),
        arguments = List(
          Argument("includeSubgroups", includeSubgroups),
          Argument("hasVulnerabilities", hasVulnerabilities),
          Argument("after", after),
          Argument("before", before),
          Argument("first", first),
          Argument("last", last)
        )
      )

    /**
      * Indicates if users can request access to namespace
      */
    def requestAccessEnabled: SelectionBuilder[Namespace, Option[Boolean]] = Field("requestAccessEnabled", OptionOf(Scalar()))

    /**
      * Aggregated storage statistics of the namespace. Only available for root namespaces
      */
    def rootStorageStatistics[A](innerSelection: SelectionBuilder[RootStorageStatistics, A]): SelectionBuilder[Namespace, Option[A]] =
      Field("rootStorageStatistics", OptionOf(Obj(innerSelection)))

    /**
      * Total storage limit of the root namespace in bytes
      */
    def storageSizeLimit: SelectionBuilder[Namespace, Option[Double]] = Field("storageSizeLimit", OptionOf(Scalar()))

    /**
      * Date until the temporary storage increase is active
      */
    def temporaryStorageIncreaseEndsOn: SelectionBuilder[Namespace, Option[Time]] =
      Field("temporaryStorageIncreaseEndsOn", OptionOf(Scalar()))

    /**
      * Visibility of the namespace
      */
    def visibility: SelectionBuilder[Namespace, Option[String]] = Field("visibility", OptionOf(Scalar()))
  }

  type NamespaceConnection

  object NamespaceConnection {

    /**
      * A list of edges.
      */
    def edges[A](innerSelection: SelectionBuilder[NamespaceEdge, A]): SelectionBuilder[NamespaceConnection, Option[List[Option[A]]]] =
      Field("edges", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * A list of nodes.
      */
    def nodes[A](innerSelection: SelectionBuilder[Namespace, A]): SelectionBuilder[NamespaceConnection, Option[List[Option[A]]]] =
      Field("nodes", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * Information to aid in pagination.
      */
    def pageInfo[A](innerSelection: SelectionBuilder[PageInfo, A]): SelectionBuilder[NamespaceConnection, A] =
      Field("pageInfo", Obj(innerSelection))
  }

  type NamespaceEdge

  object NamespaceEdge {

    /**
      * A cursor for use in pagination.
      */
    def cursor: SelectionBuilder[NamespaceEdge, String] = Field("cursor", Scalar())

    /**
      * The item at the end of the edge.
      */
    def node[A](innerSelection: SelectionBuilder[Namespace, A]): SelectionBuilder[NamespaceEdge, Option[A]] =
      Field("node", OptionOf(Obj(innerSelection)))
  }

  type Note

  object Note {

    /**
      * User who wrote this note
      */
    def author[A](innerSelection: SelectionBuilder[User, A]): SelectionBuilder[Note, A] = Field("author", Obj(innerSelection))

    /**
      * Content of the note
      */
    def body: SelectionBuilder[Note, String] = Field("body", Scalar())

    /**
      * The GitLab Flavored Markdown rendering of `note`
      */
    def bodyHtml: SelectionBuilder[Note, Option[String]] = Field("bodyHtml", OptionOf(Scalar()))

    /**
      * Indicates if this note is confidential
      */
    def confidential: SelectionBuilder[Note, Option[Boolean]] = Field("confidential", OptionOf(Scalar()))

    /**
      * Timestamp of the note creation
      */
    def createdAt: SelectionBuilder[Note, Time] = Field("createdAt", Scalar())

    /**
      * The discussion this note is a part of
      */
    def discussion[A](innerSelection: SelectionBuilder[Discussion, A]): SelectionBuilder[Note, Option[A]] =
      Field("discussion", OptionOf(Obj(innerSelection)))

    /**
      * ID of the note
      */
    def id: SelectionBuilder[Note, String] = Field("id", Scalar())

    /**
      * The position of this note on a diff
      */
    def position[A](innerSelection: SelectionBuilder[DiffPosition, A]): SelectionBuilder[Note, Option[A]] =
      Field("position", OptionOf(Obj(innerSelection)))

    /**
      * Project associated with the note
      */
    def project[A](innerSelection: SelectionBuilder[Project, A]): SelectionBuilder[Note, Option[A]] =
      Field("project", OptionOf(Obj(innerSelection)))

    /**
      * Indicates if the object can be resolved
      */
    def resolvable: SelectionBuilder[Note, Boolean] = Field("resolvable", Scalar())

    /**
      * Indicates if the object is resolved
      */
    def resolved: SelectionBuilder[Note, Boolean] = Field("resolved", Scalar())

    /**
      * Timestamp of when the object was resolved
      */
    def resolvedAt: SelectionBuilder[Note, Option[Time]] = Field("resolvedAt", OptionOf(Scalar()))

    /**
      * User who resolved the object
      */
    def resolvedBy[A](innerSelection: SelectionBuilder[User, A]): SelectionBuilder[Note, Option[A]] =
      Field("resolvedBy", OptionOf(Obj(innerSelection)))

    /**
      * Indicates whether this note was created by the system or by a user
      */
    def system: SelectionBuilder[Note, Boolean] = Field("system", Scalar())

    /**
      * Name of the icon corresponding to a system note
      */
    def systemNoteIconName: SelectionBuilder[Note, Option[String]] = Field("systemNoteIconName", OptionOf(Scalar()))

    /**
      * Timestamp of the note's last activity
      */
    def updatedAt: SelectionBuilder[Note, Time] = Field("updatedAt", Scalar())

    /**
      * Permissions for the current user on the resource
      */
    def userPermissions[A](innerSelection: SelectionBuilder[NotePermissions, A]): SelectionBuilder[Note, A] =
      Field("userPermissions", Obj(innerSelection))
  }

  type NoteConnection

  object NoteConnection {

    /**
      * A list of edges.
      */
    def edges[A](innerSelection: SelectionBuilder[NoteEdge, A]): SelectionBuilder[NoteConnection, Option[List[Option[A]]]] =
      Field("edges", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * A list of nodes.
      */
    def nodes[A](innerSelection: SelectionBuilder[Note, A]): SelectionBuilder[NoteConnection, Option[List[Option[A]]]] =
      Field("nodes", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * Information to aid in pagination.
      */
    def pageInfo[A](innerSelection: SelectionBuilder[PageInfo, A]): SelectionBuilder[NoteConnection, A] =
      Field("pageInfo", Obj(innerSelection))
  }

  type NoteEdge

  object NoteEdge {

    /**
      * A cursor for use in pagination.
      */
    def cursor: SelectionBuilder[NoteEdge, String] = Field("cursor", Scalar())

    /**
      * The item at the end of the edge.
      */
    def node[A](innerSelection: SelectionBuilder[Note, A]): SelectionBuilder[NoteEdge, Option[A]] =
      Field("node", OptionOf(Obj(innerSelection)))
  }

  type NotePermissions

  object NotePermissions {

    /**
      * Indicates the user can perform `admin_note` on this resource
      */
    def adminNote: SelectionBuilder[NotePermissions, Boolean] = Field("adminNote", Scalar())

    /**
      * Indicates the user can perform `award_emoji` on this resource
      */
    def awardEmoji: SelectionBuilder[NotePermissions, Boolean] = Field("awardEmoji", Scalar())

    /**
      * Indicates the user can perform `create_note` on this resource
      */
    def createNote: SelectionBuilder[NotePermissions, Boolean] = Field("createNote", Scalar())

    /**
      * Indicates the user can perform `read_note` on this resource
      */
    def readNote: SelectionBuilder[NotePermissions, Boolean] = Field("readNote", Scalar())

    /**
      * Indicates the user can perform `resolve_note` on this resource
      */
    def resolveNote: SelectionBuilder[NotePermissions, Boolean] = Field("resolveNote", Scalar())
  }

  type Package

  object Package {

    /**
      * The created date
      */
    def createdAt: SelectionBuilder[Package, Time] = Field("createdAt", Scalar())

    /**
      * The ID of the package
      */
    def id: SelectionBuilder[Package, String] = Field("id", Scalar())

    /**
      * The name of the package
      */
    def name: SelectionBuilder[Package, String] = Field("name", Scalar())

    /**
      * The type of the package
      */
    def packageType: SelectionBuilder[Package, PackageTypeEnum] = Field("packageType", Scalar())

    /**
      * The update date
      */
    def updatedAt: SelectionBuilder[Package, Time] = Field("updatedAt", Scalar())

    /**
      * The version of the package
      */
    def version: SelectionBuilder[Package, Option[String]] = Field("version", OptionOf(Scalar()))
  }

  type PackageConnection

  object PackageConnection {

    /**
      * A list of edges.
      */
    def edges[A](innerSelection: SelectionBuilder[PackageEdge, A]): SelectionBuilder[PackageConnection, Option[List[Option[A]]]] =
      Field("edges", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * A list of nodes.
      */
    def nodes[A](innerSelection: SelectionBuilder[Package, A]): SelectionBuilder[PackageConnection, Option[List[Option[A]]]] =
      Field("nodes", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * Information to aid in pagination.
      */
    def pageInfo[A](innerSelection: SelectionBuilder[PageInfo, A]): SelectionBuilder[PackageConnection, A] =
      Field("pageInfo", Obj(innerSelection))
  }

  type PackageEdge

  object PackageEdge {

    /**
      * A cursor for use in pagination.
      */
    def cursor: SelectionBuilder[PackageEdge, String] = Field("cursor", Scalar())

    /**
      * The item at the end of the edge.
      */
    def node[A](innerSelection: SelectionBuilder[Package, A]): SelectionBuilder[PackageEdge, Option[A]] =
      Field("node", OptionOf(Obj(innerSelection)))
  }

  type PageInfo

  object PageInfo {

    /**
      * When paginating forwards, the cursor to continue.
      */
    def endCursor: SelectionBuilder[PageInfo, Option[String]] = Field("endCursor", OptionOf(Scalar()))

    /**
      * When paginating forwards, are there more items?
      */
    def hasNextPage: SelectionBuilder[PageInfo, Boolean] = Field("hasNextPage", Scalar())

    /**
      * When paginating backwards, are there more items?
      */
    def hasPreviousPage: SelectionBuilder[PageInfo, Boolean] = Field("hasPreviousPage", Scalar())

    /**
      * When paginating backwards, the cursor to continue.
      */
    def startCursor: SelectionBuilder[PageInfo, Option[String]] = Field("startCursor", OptionOf(Scalar()))
  }

  type Pipeline

  object Pipeline {

    /**
      * Base SHA of the source branch
      */
    def beforeSha: SelectionBuilder[Pipeline, Option[String]] = Field("beforeSha", OptionOf(Scalar()))

    /**
      * Timestamp of the pipeline's commit
      */
    def committedAt: SelectionBuilder[Pipeline, Option[Time]] = Field("committedAt", OptionOf(Scalar()))

    /**
      * Coverage percentage
      */
    def coverage: SelectionBuilder[Pipeline, Option[Double]] = Field("coverage", OptionOf(Scalar()))

    /**
      * Timestamp of the pipeline's creation
      */
    def createdAt: SelectionBuilder[Pipeline, Time] = Field("createdAt", Scalar())

    /**
      * Detailed status of the pipeline
      */
    def detailedStatus[A](innerSelection: SelectionBuilder[DetailedStatus, A]): SelectionBuilder[Pipeline, A] =
      Field("detailedStatus", Obj(innerSelection))

    /**
      * Duration of the pipeline in seconds
      */
    def duration: SelectionBuilder[Pipeline, Option[Int]] = Field("duration", OptionOf(Scalar()))

    /**
      * Timestamp of the pipeline's completion
      */
    def finishedAt: SelectionBuilder[Pipeline, Option[Time]] = Field("finishedAt", OptionOf(Scalar()))

    /**
      * ID of the pipeline
      */
    def id: SelectionBuilder[Pipeline, String] = Field("id", Scalar())

    /**
      * Internal ID of the pipeline
      */
    def iid: SelectionBuilder[Pipeline, String] = Field("iid", Scalar())

    /**
      * Vulnerability and scanned resource counts for each security scanner of the pipeline
      */
    def securityReportSummary[A](innerSelection: SelectionBuilder[SecurityReportSummary, A]): SelectionBuilder[Pipeline, Option[A]] =
      Field("securityReportSummary", OptionOf(Obj(innerSelection)))

    /**
      * SHA of the pipeline's commit
      */
    def sha: SelectionBuilder[Pipeline, String] = Field("sha", Scalar())

    /**
      * Timestamp when the pipeline was started
      */
    def startedAt: SelectionBuilder[Pipeline, Option[Time]] = Field("startedAt", OptionOf(Scalar()))

    /**
      * Status of the pipeline (CREATED, WAITING_FOR_RESOURCE, PREPARING, PENDING, RUNNING, FAILED, SUCCESS, CANCELED, SKIPPED, MANUAL, SCHEDULED)
      */
    def status: SelectionBuilder[Pipeline, PipelineStatusEnum] = Field("status", Scalar())

    /**
      * Timestamp of the pipeline's last activity
      */
    def updatedAt: SelectionBuilder[Pipeline, Time] = Field("updatedAt", Scalar())

    /**
      * Permissions for the current user on the resource
      */
    def userPermissions[A](innerSelection: SelectionBuilder[PipelinePermissions, A]): SelectionBuilder[Pipeline, A] =
      Field("userPermissions", Obj(innerSelection))
  }

  type PipelineConnection

  object PipelineConnection {

    /**
      * A list of edges.
      */
    def edges[A](innerSelection: SelectionBuilder[PipelineEdge, A]): SelectionBuilder[PipelineConnection, Option[List[Option[A]]]] =
      Field("edges", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * A list of nodes.
      */
    def nodes[A](innerSelection: SelectionBuilder[Pipeline, A]): SelectionBuilder[PipelineConnection, Option[List[Option[A]]]] =
      Field("nodes", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * Information to aid in pagination.
      */
    def pageInfo[A](innerSelection: SelectionBuilder[PageInfo, A]): SelectionBuilder[PipelineConnection, A] =
      Field("pageInfo", Obj(innerSelection))
  }

  type PipelineEdge

  object PipelineEdge {

    /**
      * A cursor for use in pagination.
      */
    def cursor: SelectionBuilder[PipelineEdge, String] = Field("cursor", Scalar())

    /**
      * The item at the end of the edge.
      */
    def node[A](innerSelection: SelectionBuilder[Pipeline, A]): SelectionBuilder[PipelineEdge, Option[A]] =
      Field("node", OptionOf(Obj(innerSelection)))
  }

  type PipelinePermissions

  object PipelinePermissions {

    /**
      * Indicates the user can perform `admin_pipeline` on this resource
      */
    def adminPipeline: SelectionBuilder[PipelinePermissions, Boolean] = Field("adminPipeline", Scalar())

    /**
      * Indicates the user can perform `destroy_pipeline` on this resource
      */
    def destroyPipeline: SelectionBuilder[PipelinePermissions, Boolean] = Field("destroyPipeline", Scalar())

    /**
      * Indicates the user can perform `update_pipeline` on this resource
      */
    def updatePipeline: SelectionBuilder[PipelinePermissions, Boolean] = Field("updatePipeline", Scalar())
  }

  type Project

  object Project {

    /**
      * A single Alert Management alert of the project
      */
    def alertManagementAlert[A](
      iid: Option[String] = None,
      statuses: Option[List[AlertManagementStatus]] = None,
      sort: Option[AlertManagementAlertSort] = None,
      search: Option[String] = None
    )(
      innerSelection: SelectionBuilder[AlertManagementAlert, A]
    ): SelectionBuilder[Project, Option[A]] =
      Field(
        "alertManagementAlert",
        OptionOf(Obj(innerSelection)),
        arguments = List(Argument("iid", iid), Argument("statuses", statuses), Argument("sort", sort), Argument("search", search))
      )

    /**
      * Counts of alerts by status for the project
      */
    def alertManagementAlertStatusCounts[A](
      search: Option[String] = None
    )(
      innerSelection: SelectionBuilder[AlertManagementAlertStatusCountsType, A]
    ): SelectionBuilder[Project, Option[A]] =
      Field("alertManagementAlertStatusCounts", OptionOf(Obj(innerSelection)), arguments = List(Argument("search", search)))

    /**
      * Alert Management alerts of the project
      */
    def alertManagementAlerts[A](
      iid: Option[String] = None,
      statuses: Option[List[AlertManagementStatus]] = None,
      sort: Option[AlertManagementAlertSort] = None,
      search: Option[String] = None,
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[AlertManagementAlertConnection, A]
    ): SelectionBuilder[Project, Option[A]] =
      Field(
        "alertManagementAlerts",
        OptionOf(Obj(innerSelection)),
        arguments = List(
          Argument("iid", iid),
          Argument("statuses", statuses),
          Argument("sort", sort),
          Argument("search", search),
          Argument("after", after),
          Argument("before", before),
          Argument("first", first),
          Argument("last", last)
        )
      )

    /**
      * If `only_allow_merge_if_pipeline_succeeds` is true, indicates if merge requests of the project can also be merged with skipped jobs
      */
    def allowMergeOnSkippedPipeline: SelectionBuilder[Project, Option[Boolean]] = Field("allowMergeOnSkippedPipeline", OptionOf(Scalar()))

    /**
      * Indicates the archived status of the project
      */
    def archived: SelectionBuilder[Project, Option[Boolean]] = Field("archived", OptionOf(Scalar()))

    /**
      * Indicates if issues referenced by merge requests and commits within the default branch are closed automatically
      */
    def autocloseReferencedIssues: SelectionBuilder[Project, Option[Boolean]] = Field("autocloseReferencedIssues", OptionOf(Scalar()))

    /**
      * URL to avatar image file of the project
      */
    def avatarUrl: SelectionBuilder[Project, Option[String]] = Field("avatarUrl", OptionOf(Scalar()))

    /**
      * A single board of the project
      */
    def board[A](id: Option[String] = None)(innerSelection: SelectionBuilder[Board, A]): SelectionBuilder[Project, Option[A]] =
      Field("board", OptionOf(Obj(innerSelection)), arguments = List(Argument("id", id)))

    /**
      * Boards of the project
      */
    def boards[A](
      id: Option[String] = None,
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[BoardConnection, A]
    ): SelectionBuilder[Project, Option[A]] =
      Field(
        "boards",
        OptionOf(Obj(innerSelection)),
        arguments =
          List(Argument("id", id), Argument("after", after), Argument("before", before), Argument("first", first), Argument("last", last))
      )

    /**
      * Compliance frameworks associated with the project
      */
    def complianceFrameworks[A](
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[ComplianceFrameworkConnection, A]
    ): SelectionBuilder[Project, Option[A]] =
      Field(
        "complianceFrameworks",
        OptionOf(Obj(innerSelection)),
        arguments = List(Argument("after", after), Argument("before", before), Argument("first", first), Argument("last", last))
      )

    /**
      * The container expiration policy of the project
      */
    def containerExpirationPolicy[A](innerSelection: SelectionBuilder[ContainerExpirationPolicy, A]): SelectionBuilder[Project, Option[A]] =
      Field("containerExpirationPolicy", OptionOf(Obj(innerSelection)))

    /**
      * Indicates if the project stores Docker container images in a container registry
      */
    def containerRegistryEnabled: SelectionBuilder[Project, Option[Boolean]] = Field("containerRegistryEnabled", OptionOf(Scalar()))

    /**
      * Timestamp of the project creation
      */
    def createdAt: SelectionBuilder[Project, Option[Time]] = Field("createdAt", OptionOf(Scalar()))

    /**
      * Short description of the project
      */
    def description: SelectionBuilder[Project, Option[String]] = Field("description", OptionOf(Scalar()))

    /**
      * The GitLab Flavored Markdown rendering of `description`
      */
    def descriptionHtml: SelectionBuilder[Project, Option[String]] = Field("descriptionHtml", OptionOf(Scalar()))

    /**
      * Environments of the project
      */
    def environments[A](
      name: Option[String] = None,
      search: Option[String] = None,
      states: Option[List[String]] = None,
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[EnvironmentConnection, A]
    ): SelectionBuilder[Project, Option[A]] =
      Field(
        "environments",
        OptionOf(Obj(innerSelection)),
        arguments = List(
          Argument("name", name),
          Argument("search", search),
          Argument("states", states),
          Argument("after", after),
          Argument("before", before),
          Argument("first", first),
          Argument("last", last)
        )
      )

    /**
      * Number of times the project has been forked
      */
    def forksCount: SelectionBuilder[Project, Int] = Field("forksCount", Scalar())

    /**
      * Full path of the project
      */
    def fullPath: SelectionBuilder[Project, String] = Field("fullPath", Scalar())

    /**
      * Grafana integration details for the project
      */
    def grafanaIntegration[A](innerSelection: SelectionBuilder[GrafanaIntegration, A]): SelectionBuilder[Project, Option[A]] =
      Field("grafanaIntegration", OptionOf(Obj(innerSelection)))

    /**
      * Group of the project
      */
    def group[A](innerSelection: SelectionBuilder[Group, A]): SelectionBuilder[Project, Option[A]] =
      Field("group", OptionOf(Obj(innerSelection)))

    /**
      * URL to connect to the project via HTTPS
      */
    def httpUrlToRepo: SelectionBuilder[Project, Option[String]] = Field("httpUrlToRepo", OptionOf(Scalar()))

    /**
      * ID of the project
      */
    def id: SelectionBuilder[Project, String] = Field("id", Scalar())

    /**
      * Status of import background job of the project
      */
    def importStatus: SelectionBuilder[Project, Option[String]] = Field("importStatus", OptionOf(Scalar()))

    /**
      * A single issue of the project
      */
    def issue[A](
      iid: Option[String] = None,
      iids: Option[List[String]] = None,
      state: Option[IssuableState] = None,
      labelName: Option[List[Option[String]]] = None,
      milestoneTitle: Option[List[Option[String]]] = None,
      assigneeUsername: Option[String] = None,
      assigneeId: Option[String] = None,
      createdBefore: Option[Time] = None,
      createdAfter: Option[Time] = None,
      updatedBefore: Option[Time] = None,
      updatedAfter: Option[Time] = None,
      closedBefore: Option[Time] = None,
      closedAfter: Option[Time] = None,
      search: Option[String] = None,
      sort: Option[IssueSort] = None,
      iterationId: Option[List[Option[String]]] = None
    )(
      innerSelection: SelectionBuilder[Issue, A]
    ): SelectionBuilder[Project, Option[A]] =
      Field(
        "issue",
        OptionOf(Obj(innerSelection)),
        arguments = List(
          Argument("iid", iid),
          Argument("iids", iids),
          Argument("state", state),
          Argument("labelName", labelName),
          Argument("milestoneTitle", milestoneTitle),
          Argument("assigneeUsername", assigneeUsername),
          Argument("assigneeId", assigneeId),
          Argument("createdBefore", createdBefore),
          Argument("createdAfter", createdAfter),
          Argument("updatedBefore", updatedBefore),
          Argument("updatedAfter", updatedAfter),
          Argument("closedBefore", closedBefore),
          Argument("closedAfter", closedAfter),
          Argument("search", search),
          Argument("sort", sort),
          Argument("iterationId", iterationId)
        )
      )

    /**
      * Issues of the project
      */
    def issues[A](
      iid: Option[String] = None,
      iids: Option[List[String]] = None,
      state: Option[IssuableState] = None,
      labelName: Option[List[Option[String]]] = None,
      milestoneTitle: Option[List[Option[String]]] = None,
      assigneeUsername: Option[String] = None,
      assigneeId: Option[String] = None,
      createdBefore: Option[Time] = None,
      createdAfter: Option[Time] = None,
      updatedBefore: Option[Time] = None,
      updatedAfter: Option[Time] = None,
      closedBefore: Option[Time] = None,
      closedAfter: Option[Time] = None,
      search: Option[String] = None,
      sort: Option[IssueSort] = None,
      iterationId: Option[List[Option[String]]] = None,
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[IssueConnection, A]
    ): SelectionBuilder[Project, Option[A]] =
      Field(
        "issues",
        OptionOf(Obj(innerSelection)),
        arguments = List(
          Argument("iid", iid),
          Argument("iids", iids),
          Argument("state", state),
          Argument("labelName", labelName),
          Argument("milestoneTitle", milestoneTitle),
          Argument("assigneeUsername", assigneeUsername),
          Argument("assigneeId", assigneeId),
          Argument("createdBefore", createdBefore),
          Argument("createdAfter", createdAfter),
          Argument("updatedBefore", updatedBefore),
          Argument("updatedAfter", updatedAfter),
          Argument("closedBefore", closedBefore),
          Argument("closedAfter", closedAfter),
          Argument("search", search),
          Argument("sort", sort),
          Argument("iterationId", iterationId),
          Argument("after", after),
          Argument("before", before),
          Argument("first", first),
          Argument("last", last)
        )
      )

    /**
      * Indicates if Issues are enabled for the current user
      */
    def issuesEnabled: SelectionBuilder[Project, Option[Boolean]] = Field("issuesEnabled", OptionOf(Scalar()))

    /**
      * Status of Jira import background job of the project
      */
    def jiraImportStatus: SelectionBuilder[Project, Option[String]] = Field("jiraImportStatus", OptionOf(Scalar()))

    /**
      * Jira imports into the project
      */
    def jiraImports[A](
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[JiraImportConnection, A]
    ): SelectionBuilder[Project, Option[A]] =
      Field(
        "jiraImports",
        OptionOf(Obj(innerSelection)),
        arguments = List(Argument("after", after), Argument("before", before), Argument("first", first), Argument("last", last))
      )

    /**
      * Indicates if CI/CD pipeline jobs are enabled for the current user
      */
    def jobsEnabled: SelectionBuilder[Project, Option[Boolean]] = Field("jobsEnabled", OptionOf(Scalar()))

    /**
      * A label available on this project
      */
    def label[A](title: String)(innerSelection: SelectionBuilder[Label, A]): SelectionBuilder[Project, Option[A]] =
      Field("label", OptionOf(Obj(innerSelection)), arguments = List(Argument("title", title)))

    /**
      * Labels available on this project
      */
    def labels[A](
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None,
      searchTerm: Option[String] = None
    )(
      innerSelection: SelectionBuilder[LabelConnection, A]
    ): SelectionBuilder[Project, Option[A]] =
      Field(
        "labels",
        OptionOf(Obj(innerSelection)),
        arguments = List(
          Argument("after", after),
          Argument("before", before),
          Argument("first", first),
          Argument("last", last),
          Argument("searchTerm", searchTerm)
        )
      )

    /**
      * Timestamp of the project last activity
      */
    def lastActivityAt: SelectionBuilder[Project, Option[Time]] = Field("lastActivityAt", OptionOf(Scalar()))

    /**
      * Indicates if the project has Large File Storage (LFS) enabled
      */
    def lfsEnabled: SelectionBuilder[Project, Option[Boolean]] = Field("lfsEnabled", OptionOf(Scalar()))

    /**
      * A single merge request of the project
      */
    def mergeRequest[A](iid: String)(innerSelection: SelectionBuilder[MergeRequest, A]): SelectionBuilder[Project, Option[A]] =
      Field("mergeRequest", OptionOf(Obj(innerSelection)), arguments = List(Argument("iid", iid)))

    /**
      * Merge requests of the project
      */
    def mergeRequests[A](
      iids: Option[List[String]] = None,
      sourceBranches: Option[List[String]] = None,
      targetBranches: Option[List[String]] = None,
      state: Option[MergeRequestState] = None,
      labels: Option[List[String]] = None,
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[MergeRequestConnection, A]
    ): SelectionBuilder[Project, Option[A]] =
      Field(
        "mergeRequests",
        OptionOf(Obj(innerSelection)),
        arguments = List(
          Argument("iids", iids),
          Argument("sourceBranches", sourceBranches),
          Argument("targetBranches", targetBranches),
          Argument("state", state),
          Argument("labels", labels),
          Argument("after", after),
          Argument("before", before),
          Argument("first", first),
          Argument("last", last)
        )
      )

    /**
      * Indicates if Merge Requests are enabled for the current user
      */
    def mergeRequestsEnabled: SelectionBuilder[Project, Option[Boolean]] = Field("mergeRequestsEnabled", OptionOf(Scalar()))

    /**
      * Indicates if no merge commits should be created and all merges should instead be fast-forwarded, which means that merging is only allowed if the branch could be fast-forwarded.
      */
    def mergeRequestsFfOnlyEnabled: SelectionBuilder[Project, Option[Boolean]] = Field("mergeRequestsFfOnlyEnabled", OptionOf(Scalar()))

    /**
      * Name of the project (without namespace)
      */
    def name: SelectionBuilder[Project, String] = Field("name", Scalar())

    /**
      * Full name of the project with its namespace
      */
    def nameWithNamespace: SelectionBuilder[Project, String] = Field("nameWithNamespace", Scalar())

    /**
      * Namespace of the project
      */
    def namespace[A](innerSelection: SelectionBuilder[Namespace, A]): SelectionBuilder[Project, Option[A]] =
      Field("namespace", OptionOf(Obj(innerSelection)))

    /**
      * Indicates if merge requests of the project can only be merged when all the discussions are resolved
      */
    def onlyAllowMergeIfAllDiscussionsAreResolved: SelectionBuilder[Project, Option[Boolean]] =
      Field("onlyAllowMergeIfAllDiscussionsAreResolved", OptionOf(Scalar()))

    /**
      * Indicates if merge requests of the project can only be merged with successful jobs
      */
    def onlyAllowMergeIfPipelineSucceeds: SelectionBuilder[Project, Option[Boolean]] =
      Field("onlyAllowMergeIfPipelineSucceeds", OptionOf(Scalar()))

    /**
      * Number of open issues for the project
      */
    def openIssuesCount: SelectionBuilder[Project, Option[Int]] = Field("openIssuesCount", OptionOf(Scalar()))

    /**
      * Packages of the project
      */
    def packages[A](
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[PackageConnection, A]
    ): SelectionBuilder[Project, Option[A]] =
      Field(
        "packages",
        OptionOf(Obj(innerSelection)),
        arguments = List(Argument("after", after), Argument("before", before), Argument("first", first), Argument("last", last))
      )

    /**
      * Path of the project
      */
    def path: SelectionBuilder[Project, String] = Field("path", Scalar())

    /**
      * Build pipeline of the project
      */
    def pipeline[A](iid: String)(innerSelection: SelectionBuilder[Pipeline, A]): SelectionBuilder[Project, Option[A]] =
      Field("pipeline", OptionOf(Obj(innerSelection)), arguments = List(Argument("iid", iid)))

    /**
      * Build pipelines of the project
      */
    def pipelines[A](
      status: Option[PipelineStatusEnum] = None,
      ref: Option[String] = None,
      sha: Option[String] = None,
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[PipelineConnection, A]
    ): SelectionBuilder[Project, Option[A]] =
      Field(
        "pipelines",
        OptionOf(Obj(innerSelection)),
        arguments = List(
          Argument("status", status),
          Argument("ref", ref),
          Argument("sha", sha),
          Argument("after", after),
          Argument("before", before),
          Argument("first", first),
          Argument("last", last)
        )
      )

    /**
      * Indicates if a link to create or view a merge request should display after a push to Git repositories of the project from the command line
      */
    def printingMergeRequestLinkEnabled: SelectionBuilder[Project, Option[Boolean]] =
      Field("printingMergeRequestLinkEnabled", OptionOf(Scalar()))

    /**
      * Members of the project
      */
    def projectMembers[A](
      search: Option[String] = None,
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[ProjectMemberConnection, A]
    ): SelectionBuilder[Project, Option[A]] =
      Field(
        "projectMembers",
        OptionOf(Obj(innerSelection)),
        arguments = List(
          Argument("search", search),
          Argument("after", after),
          Argument("before", before),
          Argument("first", first),
          Argument("last", last)
        )
      )

    /**
      * Indicates if there is public access to pipelines and job details of the project, including output logs and artifacts
      */
    def publicJobs: SelectionBuilder[Project, Option[Boolean]] = Field("publicJobs", OptionOf(Scalar()))

    /**
      * A single release of the project
      */
    def release[A](tagName: String)(innerSelection: SelectionBuilder[Release, A]): SelectionBuilder[Project, Option[A]] =
      Field("release", OptionOf(Obj(innerSelection)), arguments = List(Argument("tagName", tagName)))

    /**
      * Releases of the project
      */
    def releases[A](
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[ReleaseConnection, A]
    ): SelectionBuilder[Project, Option[A]] =
      Field(
        "releases",
        OptionOf(Obj(innerSelection)),
        arguments = List(Argument("after", after), Argument("before", before), Argument("first", first), Argument("last", last))
      )

    /**
      * Indicates if `Delete source branch` option should be enabled by default for all new merge requests of the project
      */
    def removeSourceBranchAfterMerge: SelectionBuilder[Project, Option[Boolean]] = Field("removeSourceBranchAfterMerge", OptionOf(Scalar()))

    /**
      * Git repository of the project
      */
    def repository[A](innerSelection: SelectionBuilder[Repository, A]): SelectionBuilder[Project, Option[A]] =
      Field("repository", OptionOf(Obj(innerSelection)))

    /**
      * Indicates if users can request member access to the project
      */
    def requestAccessEnabled: SelectionBuilder[Project, Option[Boolean]] = Field("requestAccessEnabled", OptionOf(Scalar()))

    /**
      * Find a single requirement. Available only when feature flag `requirements_management` is enabled.
      */
    def requirement[A](
      iid: Option[String] = None,
      iids: Option[List[String]] = None,
      sort: Option[Sort] = None,
      state: Option[RequirementState] = None,
      search: Option[String] = None,
      authorUsername: Option[List[String]] = None
    )(
      innerSelection: SelectionBuilder[Requirement, A]
    ): SelectionBuilder[Project, Option[A]] =
      Field(
        "requirement",
        OptionOf(Obj(innerSelection)),
        arguments = List(
          Argument("iid", iid),
          Argument("iids", iids),
          Argument("sort", sort),
          Argument("state", state),
          Argument("search", search),
          Argument("authorUsername", authorUsername)
        )
      )

    /**
      * Number of requirements for the project by their state
      */
    def requirementStatesCount[A](innerSelection: SelectionBuilder[RequirementStatesCount, A]): SelectionBuilder[Project, Option[A]] =
      Field("requirementStatesCount", OptionOf(Obj(innerSelection)))

    /**
      * Find requirements. Available only when feature flag `requirements_management` is enabled.
      */
    def requirements[A](
      iid: Option[String] = None,
      iids: Option[List[String]] = None,
      sort: Option[Sort] = None,
      state: Option[RequirementState] = None,
      search: Option[String] = None,
      authorUsername: Option[List[String]] = None,
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[RequirementConnection, A]
    ): SelectionBuilder[Project, Option[A]] =
      Field(
        "requirements",
        OptionOf(Obj(innerSelection)),
        arguments = List(
          Argument("iid", iid),
          Argument("iids", iids),
          Argument("sort", sort),
          Argument("state", state),
          Argument("search", search),
          Argument("authorUsername", authorUsername),
          Argument("after", after),
          Argument("before", before),
          Argument("first", first),
          Argument("last", last)
        )
      )

    /**
      * SAST CI configuration for the project
      */
    def sastCiConfiguration[A](innerSelection: SelectionBuilder[SastCiConfiguration, A]): SelectionBuilder[Project, Option[A]] =
      Field("sastCiConfiguration", OptionOf(Obj(innerSelection)))

    /**
      * Information about security analyzers used in the project
      */
    def securityScanners[A](innerSelection: SelectionBuilder[SecurityScanners, A]): SelectionBuilder[Project, Option[A]] =
      Field("securityScanners", OptionOf(Obj(innerSelection)))

    /**
      * Detailed version of a Sentry error on the project
      */
    def sentryDetailedError[A](id: String)(innerSelection: SelectionBuilder[SentryDetailedError, A]): SelectionBuilder[Project, Option[A]] =
      Field("sentryDetailedError", OptionOf(Obj(innerSelection)), arguments = List(Argument("id", id)))

    /**
      * Paginated collection of Sentry errors on the project
      */
    def sentryErrors[A](innerSelection: SelectionBuilder[SentryErrorCollection, A]): SelectionBuilder[Project, Option[A]] =
      Field("sentryErrors", OptionOf(Obj(innerSelection)))

    /**
      * E-mail address of the service desk.
      */
    def serviceDeskAddress: SelectionBuilder[Project, Option[String]] = Field("serviceDeskAddress", OptionOf(Scalar()))

    /**
      * Indicates if the project has service desk enabled.
      */
    def serviceDeskEnabled: SelectionBuilder[Project, Option[Boolean]] = Field("serviceDeskEnabled", OptionOf(Scalar()))

    /**
      * Project services
      */
    def services[A](
      active: Option[Boolean] = None,
      `type`: Option[ServiceType] = None,
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[ServiceConnection, A]
    ): SelectionBuilder[Project, Option[A]] =
      Field(
        "services",
        OptionOf(Obj(innerSelection)),
        arguments = List(
          Argument("active", active),
          Argument("type", `type`),
          Argument("after", after),
          Argument("before", before),
          Argument("first", first),
          Argument("last", last)
        )
      )

    /**
      * Indicates if Shared Runners are enabled for the project
      */
    def sharedRunnersEnabled: SelectionBuilder[Project, Option[Boolean]] = Field("sharedRunnersEnabled", OptionOf(Scalar()))

    /**
      * Snippets of the project
      */
    def snippets[A](
      ids: Option[List[String]] = None,
      visibility: Option[VisibilityScopesEnum] = None,
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[SnippetConnection, A]
    ): SelectionBuilder[Project, Option[A]] =
      Field(
        "snippets",
        OptionOf(Obj(innerSelection)),
        arguments = List(
          Argument("ids", ids),
          Argument("visibility", visibility),
          Argument("after", after),
          Argument("before", before),
          Argument("first", first),
          Argument("last", last)
        )
      )

    /**
      * Indicates if Snippets are enabled for the current user
      */
    def snippetsEnabled: SelectionBuilder[Project, Option[Boolean]] = Field("snippetsEnabled", OptionOf(Scalar()))

    /**
      * URL to connect to the project via SSH
      */
    def sshUrlToRepo: SelectionBuilder[Project, Option[String]] = Field("sshUrlToRepo", OptionOf(Scalar()))

    /**
      * Number of times the project has been starred
      */
    def starCount: SelectionBuilder[Project, Int] = Field("starCount", Scalar())

    /**
      * Statistics of the project
      */
    def statistics[A](innerSelection: SelectionBuilder[ProjectStatistics, A]): SelectionBuilder[Project, Option[A]] =
      Field("statistics", OptionOf(Obj(innerSelection)))

    /**
      * The commit message used to apply merge request suggestions
      */
    def suggestionCommitMessage: SelectionBuilder[Project, Option[String]] = Field("suggestionCommitMessage", OptionOf(Scalar()))

    /**
      * List of project topics (not Git tags)
      */
    def tagList: SelectionBuilder[Project, Option[String]] = Field("tagList", OptionOf(Scalar()))

    /**
      * Permissions for the current user on the resource
      */
    def userPermissions[A](innerSelection: SelectionBuilder[ProjectPermissions, A]): SelectionBuilder[Project, A] =
      Field("userPermissions", Obj(innerSelection))

    /**
      * Visibility of the project
      */
    def visibility: SelectionBuilder[Project, Option[String]] = Field("visibility", OptionOf(Scalar()))

    /**
      * Vulnerabilities reported on the project
      */
    def vulnerabilities[A](
      projectId: Option[List[String]] = None,
      reportType: Option[List[VulnerabilityReportType]] = None,
      severity: Option[List[VulnerabilitySeverity]] = None,
      state: Option[List[VulnerabilityState]] = None,
      scanner: Option[List[String]] = None,
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[VulnerabilityConnection, A]
    ): SelectionBuilder[Project, Option[A]] =
      Field(
        "vulnerabilities",
        OptionOf(Obj(innerSelection)),
        arguments = List(
          Argument("projectId", projectId),
          Argument("reportType", reportType),
          Argument("severity", severity),
          Argument("state", state),
          Argument("scanner", scanner),
          Argument("after", after),
          Argument("before", before),
          Argument("first", first),
          Argument("last", last)
        )
      )

    /**
      * Vulnerability scanners reported on the project vulnerabilties
      */
    def vulnerabilityScanners[A](
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[VulnerabilityScannerConnection, A]
    ): SelectionBuilder[Project, Option[A]] =
      Field(
        "vulnerabilityScanners",
        OptionOf(Obj(innerSelection)),
        arguments = List(Argument("after", after), Argument("before", before), Argument("first", first), Argument("last", last))
      )

    /**
      * Counts for each severity of vulnerability of the project
      */
    def vulnerabilitySeveritiesCount[A](
      innerSelection: SelectionBuilder[VulnerabilitySeveritiesCount, A]
    ): SelectionBuilder[Project, Option[A]] = Field("vulnerabilitySeveritiesCount", OptionOf(Obj(innerSelection)))

    /**
      * Web URL of the project
      */
    def webUrl: SelectionBuilder[Project, Option[String]] = Field("webUrl", OptionOf(Scalar()))

    /**
      * Indicates if Wikis are enabled for the current user
      */
    def wikiEnabled: SelectionBuilder[Project, Option[Boolean]] = Field("wikiEnabled", OptionOf(Scalar()))
  }

  type ProjectConnection

  object ProjectConnection {

    /**
      * A list of edges.
      */
    def edges[A](innerSelection: SelectionBuilder[ProjectEdge, A]): SelectionBuilder[ProjectConnection, Option[List[Option[A]]]] =
      Field("edges", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * A list of nodes.
      */
    def nodes[A](innerSelection: SelectionBuilder[Project, A]): SelectionBuilder[ProjectConnection, Option[List[Option[A]]]] =
      Field("nodes", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * Information to aid in pagination.
      */
    def pageInfo[A](innerSelection: SelectionBuilder[PageInfo, A]): SelectionBuilder[ProjectConnection, A] =
      Field("pageInfo", Obj(innerSelection))
  }

  type ProjectEdge

  object ProjectEdge {

    /**
      * A cursor for use in pagination.
      */
    def cursor: SelectionBuilder[ProjectEdge, String] = Field("cursor", Scalar())

    /**
      * The item at the end of the edge.
      */
    def node[A](innerSelection: SelectionBuilder[Project, A]): SelectionBuilder[ProjectEdge, Option[A]] =
      Field("node", OptionOf(Obj(innerSelection)))
  }

  type ProjectMember

  object ProjectMember {

    /**
      * GitLab::Access level
      */
    def accessLevel[A](innerSelection: SelectionBuilder[AccessLevel, A]): SelectionBuilder[ProjectMember, Option[A]] =
      Field("accessLevel", OptionOf(Obj(innerSelection)))

    /**
      * Date and time the membership was created
      */
    def createdAt: SelectionBuilder[ProjectMember, Option[Time]] = Field("createdAt", OptionOf(Scalar()))

    /**
      * User that authorized membership
      */
    def createdBy[A](innerSelection: SelectionBuilder[User, A]): SelectionBuilder[ProjectMember, Option[A]] =
      Field("createdBy", OptionOf(Obj(innerSelection)))

    /**
      * Date and time the membership expires
      */
    def expiresAt: SelectionBuilder[ProjectMember, Option[Time]] = Field("expiresAt", OptionOf(Scalar()))

    /**
      * ID of the member
      */
    def id: SelectionBuilder[ProjectMember, String] = Field("id", Scalar())

    /**
      * Project that User is a member of
      */
    def project[A](innerSelection: SelectionBuilder[Project, A]): SelectionBuilder[ProjectMember, Option[A]] =
      Field("project", OptionOf(Obj(innerSelection)))

    /**
      * Date and time the membership was last updated
      */
    def updatedAt: SelectionBuilder[ProjectMember, Option[Time]] = Field("updatedAt", OptionOf(Scalar()))

    /**
      * User that is associated with the member object
      */
    def user[A](innerSelection: SelectionBuilder[User, A]): SelectionBuilder[ProjectMember, A] = Field("user", Obj(innerSelection))

    /**
      * Permissions for the current user on the resource
      */
    def userPermissions[A](innerSelection: SelectionBuilder[ProjectPermissions, A]): SelectionBuilder[ProjectMember, A] =
      Field("userPermissions", Obj(innerSelection))
  }

  type ProjectMemberConnection

  object ProjectMemberConnection {

    /**
      * A list of edges.
      */
    def edges[A](
      innerSelection: SelectionBuilder[ProjectMemberEdge, A]
    ): SelectionBuilder[ProjectMemberConnection, Option[List[Option[A]]]] = Field("edges", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * A list of nodes.
      */
    def nodes[A](innerSelection: SelectionBuilder[ProjectMember, A]): SelectionBuilder[ProjectMemberConnection, Option[List[Option[A]]]] =
      Field("nodes", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * Information to aid in pagination.
      */
    def pageInfo[A](innerSelection: SelectionBuilder[PageInfo, A]): SelectionBuilder[ProjectMemberConnection, A] =
      Field("pageInfo", Obj(innerSelection))
  }

  type ProjectMemberEdge

  object ProjectMemberEdge {

    /**
      * A cursor for use in pagination.
      */
    def cursor: SelectionBuilder[ProjectMemberEdge, String] = Field("cursor", Scalar())

    /**
      * The item at the end of the edge.
      */
    def node[A](innerSelection: SelectionBuilder[ProjectMember, A]): SelectionBuilder[ProjectMemberEdge, Option[A]] =
      Field("node", OptionOf(Obj(innerSelection)))
  }

  type ProjectPermissions

  object ProjectPermissions {

    /**
      * Indicates the user can perform `admin_operations` on this resource
      */
    def adminOperations: SelectionBuilder[ProjectPermissions, Boolean] = Field("adminOperations", Scalar())

    /**
      * Indicates the user can perform `admin_project` on this resource
      */
    def adminProject: SelectionBuilder[ProjectPermissions, Boolean] = Field("adminProject", Scalar())

    /**
      * Indicates the user can perform `admin_remote_mirror` on this resource
      */
    def adminRemoteMirror: SelectionBuilder[ProjectPermissions, Boolean] = Field("adminRemoteMirror", Scalar())

    /**
      * Indicates the user can perform `admin_wiki` on this resource
      */
    def adminWiki: SelectionBuilder[ProjectPermissions, Boolean] = Field("adminWiki", Scalar())

    /**
      * Indicates the user can perform `archive_project` on this resource
      */
    def archiveProject: SelectionBuilder[ProjectPermissions, Boolean] = Field("archiveProject", Scalar())

    /**
      * Indicates the user can perform `change_namespace` on this resource
      */
    def changeNamespace: SelectionBuilder[ProjectPermissions, Boolean] = Field("changeNamespace", Scalar())

    /**
      * Indicates the user can perform `change_visibility_level` on this resource
      */
    def changeVisibilityLevel: SelectionBuilder[ProjectPermissions, Boolean] = Field("changeVisibilityLevel", Scalar())

    /**
      * Indicates the user can perform `create_deployment` on this resource
      */
    def createDeployment: SelectionBuilder[ProjectPermissions, Boolean] = Field("createDeployment", Scalar())

    /**
      * Indicates the user can perform `create_design` on this resource
      */
    def createDesign: SelectionBuilder[ProjectPermissions, Boolean] = Field("createDesign", Scalar())

    /**
      * Indicates the user can perform `create_issue` on this resource
      */
    def createIssue: SelectionBuilder[ProjectPermissions, Boolean] = Field("createIssue", Scalar())

    /**
      * Indicates the user can perform `create_label` on this resource
      */
    def createLabel: SelectionBuilder[ProjectPermissions, Boolean] = Field("createLabel", Scalar())

    /**
      * Indicates the user can perform `create_merge_request_from` on this resource
      */
    def createMergeRequestFrom: SelectionBuilder[ProjectPermissions, Boolean] = Field("createMergeRequestFrom", Scalar())

    /**
      * Indicates the user can perform `create_merge_request_in` on this resource
      */
    def createMergeRequestIn: SelectionBuilder[ProjectPermissions, Boolean] = Field("createMergeRequestIn", Scalar())

    /**
      * Indicates the user can perform `create_pages` on this resource
      */
    def createPages: SelectionBuilder[ProjectPermissions, Boolean] = Field("createPages", Scalar())

    /**
      * Indicates the user can perform `create_pipeline` on this resource
      */
    def createPipeline: SelectionBuilder[ProjectPermissions, Boolean] = Field("createPipeline", Scalar())

    /**
      * Indicates the user can perform `create_pipeline_schedule` on this resource
      */
    def createPipelineSchedule: SelectionBuilder[ProjectPermissions, Boolean] = Field("createPipelineSchedule", Scalar())

    /**
      * Indicates the user can perform `create_snippet` on this resource
      */
    def createSnippet: SelectionBuilder[ProjectPermissions, Boolean] = Field("createSnippet", Scalar())

    /**
      * Indicates the user can perform `create_wiki` on this resource
      */
    def createWiki: SelectionBuilder[ProjectPermissions, Boolean] = Field("createWiki", Scalar())

    /**
      * Indicates the user can perform `destroy_design` on this resource
      */
    def destroyDesign: SelectionBuilder[ProjectPermissions, Boolean] = Field("destroyDesign", Scalar())

    /**
      * Indicates the user can perform `destroy_pages` on this resource
      */
    def destroyPages: SelectionBuilder[ProjectPermissions, Boolean] = Field("destroyPages", Scalar())

    /**
      * Indicates the user can perform `destroy_wiki` on this resource
      */
    def destroyWiki: SelectionBuilder[ProjectPermissions, Boolean] = Field("destroyWiki", Scalar())

    /**
      * Indicates the user can perform `download_code` on this resource
      */
    def downloadCode: SelectionBuilder[ProjectPermissions, Boolean] = Field("downloadCode", Scalar())

    /**
      * Indicates the user can perform `download_wiki_code` on this resource
      */
    def downloadWikiCode: SelectionBuilder[ProjectPermissions, Boolean] = Field("downloadWikiCode", Scalar())

    /**
      * Indicates the user can perform `fork_project` on this resource
      */
    def forkProject: SelectionBuilder[ProjectPermissions, Boolean] = Field("forkProject", Scalar())

    /**
      * Indicates the user can perform `push_code` on this resource
      */
    def pushCode: SelectionBuilder[ProjectPermissions, Boolean] = Field("pushCode", Scalar())

    /**
      * Indicates the user can perform `push_to_delete_protected_branch` on this resource
      */
    def pushToDeleteProtectedBranch: SelectionBuilder[ProjectPermissions, Boolean] = Field("pushToDeleteProtectedBranch", Scalar())

    /**
      * Indicates the user can perform `read_commit_status` on this resource
      */
    def readCommitStatus: SelectionBuilder[ProjectPermissions, Boolean] = Field("readCommitStatus", Scalar())

    /**
      * Indicates the user can perform `read_cycle_analytics` on this resource
      */
    def readCycleAnalytics: SelectionBuilder[ProjectPermissions, Boolean] = Field("readCycleAnalytics", Scalar())

    /**
      * Indicates the user can perform `read_design` on this resource
      */
    def readDesign: SelectionBuilder[ProjectPermissions, Boolean] = Field("readDesign", Scalar())

    /**
      * Indicates the user can perform `read_merge_request` on this resource
      */
    def readMergeRequest: SelectionBuilder[ProjectPermissions, Boolean] = Field("readMergeRequest", Scalar())

    /**
      * Indicates the user can perform `read_pages_content` on this resource
      */
    def readPagesContent: SelectionBuilder[ProjectPermissions, Boolean] = Field("readPagesContent", Scalar())

    /**
      * Indicates the user can perform `read_project` on this resource
      */
    def readProject: SelectionBuilder[ProjectPermissions, Boolean] = Field("readProject", Scalar())

    /**
      * Indicates the user can perform `read_project_member` on this resource
      */
    def readProjectMember: SelectionBuilder[ProjectPermissions, Boolean] = Field("readProjectMember", Scalar())

    /**
      * Indicates the user can perform `read_wiki` on this resource
      */
    def readWiki: SelectionBuilder[ProjectPermissions, Boolean] = Field("readWiki", Scalar())

    /**
      * Indicates the user can perform `remove_fork_project` on this resource
      */
    def removeForkProject: SelectionBuilder[ProjectPermissions, Boolean] = Field("removeForkProject", Scalar())

    /**
      * Indicates the user can perform `remove_pages` on this resource
      */
    def removePages: SelectionBuilder[ProjectPermissions, Boolean] = Field("removePages", Scalar())

    /**
      * Indicates the user can perform `remove_project` on this resource
      */
    def removeProject: SelectionBuilder[ProjectPermissions, Boolean] = Field("removeProject", Scalar())

    /**
      * Indicates the user can perform `rename_project` on this resource
      */
    def renameProject: SelectionBuilder[ProjectPermissions, Boolean] = Field("renameProject", Scalar())

    /**
      * Indicates the user can perform `request_access` on this resource
      */
    def requestAccess: SelectionBuilder[ProjectPermissions, Boolean] = Field("requestAccess", Scalar())

    /**
      * Indicates the user can perform `update_pages` on this resource
      */
    def updatePages: SelectionBuilder[ProjectPermissions, Boolean] = Field("updatePages", Scalar())

    /**
      * Indicates the user can perform `update_wiki` on this resource
      */
    def updateWiki: SelectionBuilder[ProjectPermissions, Boolean] = Field("updateWiki", Scalar())

    /**
      * Indicates the user can perform `upload_file` on this resource
      */
    def uploadFile: SelectionBuilder[ProjectPermissions, Boolean] = Field("uploadFile", Scalar())
  }

  type ProjectStatistics

  object ProjectStatistics {

    /**
      * Build artifacts size of the project
      */
    def buildArtifactsSize: SelectionBuilder[ProjectStatistics, Double] = Field("buildArtifactsSize", Scalar())

    /**
      * Commit count of the project
      */
    def commitCount: SelectionBuilder[ProjectStatistics, Double] = Field("commitCount", Scalar())

    /**
      * Large File Storage (LFS) object size of the project
      */
    def lfsObjectsSize: SelectionBuilder[ProjectStatistics, Double] = Field("lfsObjectsSize", Scalar())

    /**
      * Packages size of the project
      */
    def packagesSize: SelectionBuilder[ProjectStatistics, Double] = Field("packagesSize", Scalar())

    /**
      * Repository size of the project
      */
    def repositorySize: SelectionBuilder[ProjectStatistics, Double] = Field("repositorySize", Scalar())

    /**
      * Snippets size of the project
      */
    def snippetsSize: SelectionBuilder[ProjectStatistics, Option[Double]] = Field("snippetsSize", OptionOf(Scalar()))

    /**
      * Storage size of the project
      */
    def storageSize: SelectionBuilder[ProjectStatistics, Double] = Field("storageSize", Scalar())

    /**
      * Wiki size of the project
      */
    def wikiSize: SelectionBuilder[ProjectStatistics, Option[Double]] = Field("wikiSize", OptionOf(Scalar()))
  }

  type Release

  object Release {

    /**
      * Assets of the release
      */
    def assets[A](innerSelection: SelectionBuilder[ReleaseAssets, A]): SelectionBuilder[Release, Option[A]] =
      Field("assets", OptionOf(Obj(innerSelection)))

    /**
      * User that created the release
      */
    def author[A](innerSelection: SelectionBuilder[User, A]): SelectionBuilder[Release, Option[A]] =
      Field("author", OptionOf(Obj(innerSelection)))

    /**
      * The commit associated with the release
      */
    def commit[A](innerSelection: SelectionBuilder[Commit, A]): SelectionBuilder[Release, Option[A]] =
      Field("commit", OptionOf(Obj(innerSelection)))

    /**
      * Timestamp of when the release was created
      */
    def createdAt: SelectionBuilder[Release, Option[Time]] = Field("createdAt", OptionOf(Scalar()))

    /**
      * Description (also known as "release notes") of the release
      */
    def description: SelectionBuilder[Release, Option[String]] = Field("description", OptionOf(Scalar()))

    /**
      * The GitLab Flavored Markdown rendering of `description`
      */
    def descriptionHtml: SelectionBuilder[Release, Option[String]] = Field("descriptionHtml", OptionOf(Scalar()))

    /**
      * Evidence for the release
      */
    def evidences[A](
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[ReleaseEvidenceConnection, A]
    ): SelectionBuilder[Release, Option[A]] =
      Field(
        "evidences",
        OptionOf(Obj(innerSelection)),
        arguments = List(Argument("after", after), Argument("before", before), Argument("first", first), Argument("last", last))
      )

    /**
      * Links of the release
      */
    def links[A](innerSelection: SelectionBuilder[ReleaseLinks, A]): SelectionBuilder[Release, Option[A]] =
      Field("links", OptionOf(Obj(innerSelection)))

    /**
      * Milestones associated to the release
      */
    def milestones[A](
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[MilestoneConnection, A]
    ): SelectionBuilder[Release, Option[A]] =
      Field(
        "milestones",
        OptionOf(Obj(innerSelection)),
        arguments = List(Argument("after", after), Argument("before", before), Argument("first", first), Argument("last", last))
      )

    /**
      * Name of the release
      */
    def name: SelectionBuilder[Release, Option[String]] = Field("name", OptionOf(Scalar()))

    /**
      * Timestamp of when the release was released
      */
    def releasedAt: SelectionBuilder[Release, Option[Time]] = Field("releasedAt", OptionOf(Scalar()))

    /**
      * Name of the tag associated with the release
      */
    def tagName: SelectionBuilder[Release, Option[String]] = Field("tagName", OptionOf(Scalar()))

    /**
      * Relative web path to the tag associated with the release
      */
    def tagPath: SelectionBuilder[Release, Option[String]] = Field("tagPath", OptionOf(Scalar()))
  }

  type ReleaseAssetLink

  object ReleaseAssetLink {

    /**
      * Indicates the link points to an external resource
      */
    def external: SelectionBuilder[ReleaseAssetLink, Option[Boolean]] = Field("external", OptionOf(Scalar()))

    /**
      * ID of the link
      */
    def id: SelectionBuilder[ReleaseAssetLink, String] = Field("id", Scalar())

    /**
      * Type of the link: `other`, `runbook`, `image`, `package`; defaults to `other`
      */
    def linkType: SelectionBuilder[ReleaseAssetLink, Option[ReleaseAssetLinkType]] = Field("linkType", OptionOf(Scalar()))

    /**
      * Name of the link
      */
    def name: SelectionBuilder[ReleaseAssetLink, Option[String]] = Field("name", OptionOf(Scalar()))

    /**
      * URL of the link
      */
    def url: SelectionBuilder[ReleaseAssetLink, Option[String]] = Field("url", OptionOf(Scalar()))
  }

  type ReleaseAssetLinkConnection

  object ReleaseAssetLinkConnection {

    /**
      * A list of edges.
      */
    def edges[A](
      innerSelection: SelectionBuilder[ReleaseAssetLinkEdge, A]
    ): SelectionBuilder[ReleaseAssetLinkConnection, Option[List[Option[A]]]] =
      Field("edges", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * A list of nodes.
      */
    def nodes[A](
      innerSelection: SelectionBuilder[ReleaseAssetLink, A]
    ): SelectionBuilder[ReleaseAssetLinkConnection, Option[List[Option[A]]]] =
      Field("nodes", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * Information to aid in pagination.
      */
    def pageInfo[A](innerSelection: SelectionBuilder[PageInfo, A]): SelectionBuilder[ReleaseAssetLinkConnection, A] =
      Field("pageInfo", Obj(innerSelection))
  }

  type ReleaseAssetLinkEdge

  object ReleaseAssetLinkEdge {

    /**
      * A cursor for use in pagination.
      */
    def cursor: SelectionBuilder[ReleaseAssetLinkEdge, String] = Field("cursor", Scalar())

    /**
      * The item at the end of the edge.
      */
    def node[A](innerSelection: SelectionBuilder[ReleaseAssetLink, A]): SelectionBuilder[ReleaseAssetLinkEdge, Option[A]] =
      Field("node", OptionOf(Obj(innerSelection)))
  }

  type ReleaseAssets

  object ReleaseAssets {

    /**
      * Number of assets of the release
      */
    def count: SelectionBuilder[ReleaseAssets, Option[Int]] = Field("count", OptionOf(Scalar()))

    /**
      * Asset links of the release
      */
    def links[A](
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[ReleaseAssetLinkConnection, A]
    ): SelectionBuilder[ReleaseAssets, Option[A]] =
      Field(
        "links",
        OptionOf(Obj(innerSelection)),
        arguments = List(Argument("after", after), Argument("before", before), Argument("first", first), Argument("last", last))
      )

    /**
      * Sources of the release
      */
    def sources[A](
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[ReleaseSourceConnection, A]
    ): SelectionBuilder[ReleaseAssets, Option[A]] =
      Field(
        "sources",
        OptionOf(Obj(innerSelection)),
        arguments = List(Argument("after", after), Argument("before", before), Argument("first", first), Argument("last", last))
      )

  }

  type ReleaseConnection

  object ReleaseConnection {

    /**
      * A list of edges.
      */
    def edges[A](innerSelection: SelectionBuilder[ReleaseEdge, A]): SelectionBuilder[ReleaseConnection, Option[List[Option[A]]]] =
      Field("edges", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * A list of nodes.
      */
    def nodes[A](innerSelection: SelectionBuilder[Release, A]): SelectionBuilder[ReleaseConnection, Option[List[Option[A]]]] =
      Field("nodes", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * Information to aid in pagination.
      */
    def pageInfo[A](innerSelection: SelectionBuilder[PageInfo, A]): SelectionBuilder[ReleaseConnection, A] =
      Field("pageInfo", Obj(innerSelection))
  }

  type ReleaseEdge

  object ReleaseEdge {

    /**
      * A cursor for use in pagination.
      */
    def cursor: SelectionBuilder[ReleaseEdge, String] = Field("cursor", Scalar())

    /**
      * The item at the end of the edge.
      */
    def node[A](innerSelection: SelectionBuilder[Release, A]): SelectionBuilder[ReleaseEdge, Option[A]] =
      Field("node", OptionOf(Obj(innerSelection)))
  }

  type ReleaseEvidence

  object ReleaseEvidence {

    /**
      * Timestamp when the evidence was collected
      */
    def collectedAt: SelectionBuilder[ReleaseEvidence, Option[Time]] = Field("collectedAt", OptionOf(Scalar()))

    /**
      * URL from where the evidence can be downloaded
      */
    def filepath: SelectionBuilder[ReleaseEvidence, Option[String]] = Field("filepath", OptionOf(Scalar()))

    /**
      * ID of the evidence
      */
    def id: SelectionBuilder[ReleaseEvidence, String] = Field("id", Scalar())

    /**
      * SHA1 ID of the evidence hash
      */
    def sha: SelectionBuilder[ReleaseEvidence, Option[String]] = Field("sha", OptionOf(Scalar()))
  }

  type ReleaseEvidenceConnection

  object ReleaseEvidenceConnection {

    /**
      * A list of edges.
      */
    def edges[A](
      innerSelection: SelectionBuilder[ReleaseEvidenceEdge, A]
    ): SelectionBuilder[ReleaseEvidenceConnection, Option[List[Option[A]]]] =
      Field("edges", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * A list of nodes.
      */
    def nodes[A](
      innerSelection: SelectionBuilder[ReleaseEvidence, A]
    ): SelectionBuilder[ReleaseEvidenceConnection, Option[List[Option[A]]]] =
      Field("nodes", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * Information to aid in pagination.
      */
    def pageInfo[A](innerSelection: SelectionBuilder[PageInfo, A]): SelectionBuilder[ReleaseEvidenceConnection, A] =
      Field("pageInfo", Obj(innerSelection))
  }

  type ReleaseEvidenceEdge

  object ReleaseEvidenceEdge {

    /**
      * A cursor for use in pagination.
      */
    def cursor: SelectionBuilder[ReleaseEvidenceEdge, String] = Field("cursor", Scalar())

    /**
      * The item at the end of the edge.
      */
    def node[A](innerSelection: SelectionBuilder[ReleaseEvidence, A]): SelectionBuilder[ReleaseEvidenceEdge, Option[A]] =
      Field("node", OptionOf(Obj(innerSelection)))
  }

  type ReleaseLinks

  object ReleaseLinks {

    /**
      * HTTP URL of the release's edit page
      */
    def editUrl: SelectionBuilder[ReleaseLinks, Option[String]] = Field("editUrl", OptionOf(Scalar()))

    /**
      * HTTP URL of the issues page filtered by this release
      */
    def issuesUrl: SelectionBuilder[ReleaseLinks, Option[String]] = Field("issuesUrl", OptionOf(Scalar()))

    /**
      * HTTP URL of the merge request page filtered by this release
      */
    def mergeRequestsUrl: SelectionBuilder[ReleaseLinks, Option[String]] = Field("mergeRequestsUrl", OptionOf(Scalar()))

    /**
      * HTTP URL of the release
      */
    def selfUrl: SelectionBuilder[ReleaseLinks, Option[String]] = Field("selfUrl", OptionOf(Scalar()))
  }

  type ReleaseSource

  object ReleaseSource {

    /**
      * Format of the source
      */
    def format: SelectionBuilder[ReleaseSource, Option[String]] = Field("format", OptionOf(Scalar()))

    /**
      * Download URL of the source
      */
    def url: SelectionBuilder[ReleaseSource, Option[String]] = Field("url", OptionOf(Scalar()))
  }

  type ReleaseSourceConnection

  object ReleaseSourceConnection {

    /**
      * A list of edges.
      */
    def edges[A](
      innerSelection: SelectionBuilder[ReleaseSourceEdge, A]
    ): SelectionBuilder[ReleaseSourceConnection, Option[List[Option[A]]]] = Field("edges", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * A list of nodes.
      */
    def nodes[A](innerSelection: SelectionBuilder[ReleaseSource, A]): SelectionBuilder[ReleaseSourceConnection, Option[List[Option[A]]]] =
      Field("nodes", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * Information to aid in pagination.
      */
    def pageInfo[A](innerSelection: SelectionBuilder[PageInfo, A]): SelectionBuilder[ReleaseSourceConnection, A] =
      Field("pageInfo", Obj(innerSelection))
  }

  type ReleaseSourceEdge

  object ReleaseSourceEdge {

    /**
      * A cursor for use in pagination.
      */
    def cursor: SelectionBuilder[ReleaseSourceEdge, String] = Field("cursor", Scalar())

    /**
      * The item at the end of the edge.
      */
    def node[A](innerSelection: SelectionBuilder[ReleaseSource, A]): SelectionBuilder[ReleaseSourceEdge, Option[A]] =
      Field("node", OptionOf(Obj(innerSelection)))
  }

  type RemoveAwardEmojiPayload

  object RemoveAwardEmojiPayload {

    /**
      * The award emoji after mutation
      */
    def awardEmoji[A](innerSelection: SelectionBuilder[AwardEmoji, A]): SelectionBuilder[RemoveAwardEmojiPayload, Option[A]] =
      Field("awardEmoji", OptionOf(Obj(innerSelection)))

    /**
      * A unique identifier for the client performing the mutation.
      */
    def clientMutationId: SelectionBuilder[RemoveAwardEmojiPayload, Option[String]] = Field("clientMutationId", OptionOf(Scalar()))

    /**
      * Errors encountered during execution of the mutation.
      */
    def errors: SelectionBuilder[RemoveAwardEmojiPayload, List[String]] = Field("errors", ListOf(Scalar()))
  }

  type RemoveProjectFromSecurityDashboardPayload

  object RemoveProjectFromSecurityDashboardPayload {

    /**
      * A unique identifier for the client performing the mutation.
      */
    def clientMutationId: SelectionBuilder[RemoveProjectFromSecurityDashboardPayload, Option[String]] =
      Field("clientMutationId", OptionOf(Scalar()))

    /**
      * Errors encountered during execution of the mutation.
      */
    def errors: SelectionBuilder[RemoveProjectFromSecurityDashboardPayload, List[String]] = Field("errors", ListOf(Scalar()))
  }

  type Repository

  object Repository {

    /**
      * Indicates repository has no visible content
      */
    def empty: SelectionBuilder[Repository, Boolean] = Field("empty", Scalar())

    /**
      * Indicates a corresponding Git repository exists on disk
      */
    def exists: SelectionBuilder[Repository, Boolean] = Field("exists", Scalar())

    /**
      * Default branch of the repository
      */
    def rootRef: SelectionBuilder[Repository, Option[String]] = Field("rootRef", OptionOf(Scalar()))

    /**
      * Tree of the repository
      */
    def tree[A](
      path: Option[String] = None,
      ref: Option[String] = None,
      recursive: Option[Boolean] = None
    )(
      innerSelection: SelectionBuilder[Tree, A]
    ): SelectionBuilder[Repository, Option[A]] =
      Field(
        "tree",
        OptionOf(Obj(innerSelection)),
        arguments = List(Argument("path", path), Argument("ref", ref), Argument("recursive", recursive))
      )

  }

  type Requirement

  object Requirement {

    /**
      * Author of the requirement
      */
    def author[A](innerSelection: SelectionBuilder[User, A]): SelectionBuilder[Requirement, A] = Field("author", Obj(innerSelection))

    /**
      * Timestamp of when the requirement was created
      */
    def createdAt: SelectionBuilder[Requirement, Time] = Field("createdAt", Scalar())

    /**
      * ID of the requirement
      */
    def id: SelectionBuilder[Requirement, String] = Field("id", Scalar())

    /**
      * Internal ID of the requirement
      */
    def iid: SelectionBuilder[Requirement, String] = Field("iid", Scalar())

    /**
      * Project to which the requirement belongs
      */
    def project[A](innerSelection: SelectionBuilder[Project, A]): SelectionBuilder[Requirement, A] = Field("project", Obj(innerSelection))

    /**
      * State of the requirement
      */
    def state: SelectionBuilder[Requirement, RequirementState] = Field("state", Scalar())

    /**
      * Test reports of the requirement
      */
    def testReports[A](
      sort: Option[Sort] = None,
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[TestReportConnection, A]
    ): SelectionBuilder[Requirement, Option[A]] =
      Field(
        "testReports",
        OptionOf(Obj(innerSelection)),
        arguments = List(
          Argument("sort", sort),
          Argument("after", after),
          Argument("before", before),
          Argument("first", first),
          Argument("last", last)
        )
      )

    /**
      * Title of the requirement
      */
    def title: SelectionBuilder[Requirement, Option[String]] = Field("title", OptionOf(Scalar()))

    /**
      * Timestamp of when the requirement was last updated
      */
    def updatedAt: SelectionBuilder[Requirement, Time] = Field("updatedAt", Scalar())

    /**
      * Permissions for the current user on the resource
      */
    def userPermissions[A](innerSelection: SelectionBuilder[RequirementPermissions, A]): SelectionBuilder[Requirement, A] =
      Field("userPermissions", Obj(innerSelection))
  }

  type RequirementConnection

  object RequirementConnection {

    /**
      * A list of edges.
      */
    def edges[A](innerSelection: SelectionBuilder[RequirementEdge, A]): SelectionBuilder[RequirementConnection, Option[List[Option[A]]]] =
      Field("edges", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * A list of nodes.
      */
    def nodes[A](innerSelection: SelectionBuilder[Requirement, A]): SelectionBuilder[RequirementConnection, Option[List[Option[A]]]] =
      Field("nodes", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * Information to aid in pagination.
      */
    def pageInfo[A](innerSelection: SelectionBuilder[PageInfo, A]): SelectionBuilder[RequirementConnection, A] =
      Field("pageInfo", Obj(innerSelection))
  }

  type RequirementEdge

  object RequirementEdge {

    /**
      * A cursor for use in pagination.
      */
    def cursor: SelectionBuilder[RequirementEdge, String] = Field("cursor", Scalar())

    /**
      * The item at the end of the edge.
      */
    def node[A](innerSelection: SelectionBuilder[Requirement, A]): SelectionBuilder[RequirementEdge, Option[A]] =
      Field("node", OptionOf(Obj(innerSelection)))
  }

  type RequirementPermissions

  object RequirementPermissions {

    /**
      * Indicates the user can perform `admin_requirement` on this resource
      */
    def adminRequirement: SelectionBuilder[RequirementPermissions, Boolean] = Field("adminRequirement", Scalar())

    /**
      * Indicates the user can perform `create_requirement` on this resource
      */
    def createRequirement: SelectionBuilder[RequirementPermissions, Boolean] = Field("createRequirement", Scalar())

    /**
      * Indicates the user can perform `destroy_requirement` on this resource
      */
    def destroyRequirement: SelectionBuilder[RequirementPermissions, Boolean] = Field("destroyRequirement", Scalar())

    /**
      * Indicates the user can perform `read_requirement` on this resource
      */
    def readRequirement: SelectionBuilder[RequirementPermissions, Boolean] = Field("readRequirement", Scalar())

    /**
      * Indicates the user can perform `update_requirement` on this resource
      */
    def updateRequirement: SelectionBuilder[RequirementPermissions, Boolean] = Field("updateRequirement", Scalar())
  }

  type RequirementStatesCount

  object RequirementStatesCount {

    /**
      * Number of archived requirements
      */
    def archived: SelectionBuilder[RequirementStatesCount, Option[Int]] = Field("archived", OptionOf(Scalar()))

    /**
      * Number of opened requirements
      */
    def opened: SelectionBuilder[RequirementStatesCount, Option[Int]] = Field("opened", OptionOf(Scalar()))
  }

  type RootStorageStatistics

  object RootStorageStatistics {

    /**
      * The CI artifacts size in bytes
      */
    def buildArtifactsSize: SelectionBuilder[RootStorageStatistics, Double] = Field("buildArtifactsSize", Scalar())

    /**
      * The LFS objects size in bytes
      */
    def lfsObjectsSize: SelectionBuilder[RootStorageStatistics, Double] = Field("lfsObjectsSize", Scalar())

    /**
      * The packages size in bytes
      */
    def packagesSize: SelectionBuilder[RootStorageStatistics, Double] = Field("packagesSize", Scalar())

    /**
      * The Git repository size in bytes
      */
    def repositorySize: SelectionBuilder[RootStorageStatistics, Double] = Field("repositorySize", Scalar())

    /**
      * The snippets size in bytes
      */
    def snippetsSize: SelectionBuilder[RootStorageStatistics, Double] = Field("snippetsSize", Scalar())

    /**
      * The total storage in bytes
      */
    def storageSize: SelectionBuilder[RootStorageStatistics, Double] = Field("storageSize", Scalar())

    /**
      * The wiki size in bytes
      */
    def wikiSize: SelectionBuilder[RootStorageStatistics, Double] = Field("wikiSize", Scalar())
  }

  type RunDASTScanPayload

  object RunDASTScanPayload {

    /**
      * A unique identifier for the client performing the mutation.
      */
    def clientMutationId: SelectionBuilder[RunDASTScanPayload, Option[String]] = Field("clientMutationId", OptionOf(Scalar()))

    /**
      * Errors encountered during execution of the mutation.
      */
    def errors: SelectionBuilder[RunDASTScanPayload, List[String]] = Field("errors", ListOf(Scalar()))

    /**
      * URL of the pipeline that was created.
      */
    def pipelineUrl: SelectionBuilder[RunDASTScanPayload, Option[String]] = Field("pipelineUrl", OptionOf(Scalar()))
  }

  type SastCiConfiguration

  object SastCiConfiguration {

    /**
      * List of analyzers entities attached to SAST configuration.
      */
    def analyzers[A](
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[SastCiConfigurationAnalyzersEntityConnection, A]
    ): SelectionBuilder[SastCiConfiguration, Option[A]] =
      Field(
        "analyzers",
        OptionOf(Obj(innerSelection)),
        arguments = List(Argument("after", after), Argument("before", before), Argument("first", first), Argument("last", last))
      )

    /**
      * List of global entities related to SAST configuration.
      */
    def global[A](
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[SastCiConfigurationEntityConnection, A]
    ): SelectionBuilder[SastCiConfiguration, Option[A]] =
      Field(
        "global",
        OptionOf(Obj(innerSelection)),
        arguments = List(Argument("after", after), Argument("before", before), Argument("first", first), Argument("last", last))
      )

    /**
      * List of pipeline entities related to SAST configuration.
      */
    def pipeline[A](
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[SastCiConfigurationEntityConnection, A]
    ): SelectionBuilder[SastCiConfiguration, Option[A]] =
      Field(
        "pipeline",
        OptionOf(Obj(innerSelection)),
        arguments = List(Argument("after", after), Argument("before", before), Argument("first", first), Argument("last", last))
      )

  }

  type SastCiConfigurationAnalyzersEntity

  object SastCiConfigurationAnalyzersEntity {

    /**
      * Analyzer description that is displayed on the form.
      */
    def description: SelectionBuilder[SastCiConfigurationAnalyzersEntity, Option[String]] = Field("description", OptionOf(Scalar()))

    /**
      * Indicates whether an analyzer is enabled.
      */
    def enabled: SelectionBuilder[SastCiConfigurationAnalyzersEntity, Option[Boolean]] = Field("enabled", OptionOf(Scalar()))

    /**
      * Analyzer label used in the config UI.
      */
    def label: SelectionBuilder[SastCiConfigurationAnalyzersEntity, Option[String]] = Field("label", OptionOf(Scalar()))

    /**
      * Name of the analyzer.
      */
    def name: SelectionBuilder[SastCiConfigurationAnalyzersEntity, Option[String]] = Field("name", OptionOf(Scalar()))
  }

  type SastCiConfigurationAnalyzersEntityConnection

  object SastCiConfigurationAnalyzersEntityConnection {

    /**
      * A list of edges.
      */
    def edges[A](
      innerSelection: SelectionBuilder[SastCiConfigurationAnalyzersEntityEdge, A]
    ): SelectionBuilder[SastCiConfigurationAnalyzersEntityConnection, Option[List[Option[A]]]] =
      Field("edges", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * A list of nodes.
      */
    def nodes[A](
      innerSelection: SelectionBuilder[SastCiConfigurationAnalyzersEntity, A]
    ): SelectionBuilder[SastCiConfigurationAnalyzersEntityConnection, Option[List[Option[A]]]] =
      Field("nodes", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * Information to aid in pagination.
      */
    def pageInfo[A](innerSelection: SelectionBuilder[PageInfo, A]): SelectionBuilder[SastCiConfigurationAnalyzersEntityConnection, A] =
      Field("pageInfo", Obj(innerSelection))
  }

  type SastCiConfigurationAnalyzersEntityEdge

  object SastCiConfigurationAnalyzersEntityEdge {

    /**
      * A cursor for use in pagination.
      */
    def cursor: SelectionBuilder[SastCiConfigurationAnalyzersEntityEdge, String] = Field("cursor", Scalar())

    /**
      * The item at the end of the edge.
      */
    def node[A](
      innerSelection: SelectionBuilder[SastCiConfigurationAnalyzersEntity, A]
    ): SelectionBuilder[SastCiConfigurationAnalyzersEntityEdge, Option[A]] = Field("node", OptionOf(Obj(innerSelection)))

  }

  type SastCiConfigurationEntity

  object SastCiConfigurationEntity {

    /**
      * Default value that is used if value is empty.
      */
    def defaultValue: SelectionBuilder[SastCiConfigurationEntity, Option[String]] = Field("defaultValue", OptionOf(Scalar()))

    /**
      * Entity description that is displayed on the form.
      */
    def description: SelectionBuilder[SastCiConfigurationEntity, Option[String]] = Field("description", OptionOf(Scalar()))

    /**
      * CI keyword of entity.
      */
    def field: SelectionBuilder[SastCiConfigurationEntity, Option[String]] = Field("field", OptionOf(Scalar()))

    /**
      * Label for entity used in the form.
      */
    def label: SelectionBuilder[SastCiConfigurationEntity, Option[String]] = Field("label", OptionOf(Scalar()))

    /**
      * Different possible values of the field.
      */
    def options[A](
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[SastCiConfigurationOptionsEntityConnection, A]
    ): SelectionBuilder[SastCiConfigurationEntity, Option[A]] =
      Field(
        "options",
        OptionOf(Obj(innerSelection)),
        arguments = List(Argument("after", after), Argument("before", before), Argument("first", first), Argument("last", last))
      )

    /**
      * Type of the field value.
      */
    def `type`: SelectionBuilder[SastCiConfigurationEntity, Option[String]] = Field("type", OptionOf(Scalar()))

    /**
      * Current value of the entity.
      */
    def value: SelectionBuilder[SastCiConfigurationEntity, Option[String]] = Field("value", OptionOf(Scalar()))
  }

  type SastCiConfigurationEntityConnection

  object SastCiConfigurationEntityConnection {

    /**
      * A list of edges.
      */
    def edges[A](
      innerSelection: SelectionBuilder[SastCiConfigurationEntityEdge, A]
    ): SelectionBuilder[SastCiConfigurationEntityConnection, Option[List[Option[A]]]] =
      Field("edges", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * A list of nodes.
      */
    def nodes[A](
      innerSelection: SelectionBuilder[SastCiConfigurationEntity, A]
    ): SelectionBuilder[SastCiConfigurationEntityConnection, Option[List[Option[A]]]] =
      Field("nodes", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * Information to aid in pagination.
      */
    def pageInfo[A](innerSelection: SelectionBuilder[PageInfo, A]): SelectionBuilder[SastCiConfigurationEntityConnection, A] =
      Field("pageInfo", Obj(innerSelection))
  }

  type SastCiConfigurationEntityEdge

  object SastCiConfigurationEntityEdge {

    /**
      * A cursor for use in pagination.
      */
    def cursor: SelectionBuilder[SastCiConfigurationEntityEdge, String] = Field("cursor", Scalar())

    /**
      * The item at the end of the edge.
      */
    def node[A](
      innerSelection: SelectionBuilder[SastCiConfigurationEntity, A]
    ): SelectionBuilder[SastCiConfigurationEntityEdge, Option[A]] = Field("node", OptionOf(Obj(innerSelection)))

  }

  type SastCiConfigurationOptionsEntity

  object SastCiConfigurationOptionsEntity {

    /**
      * Label of option entity.
      */
    def label: SelectionBuilder[SastCiConfigurationOptionsEntity, Option[String]] = Field("label", OptionOf(Scalar()))

    /**
      * Value of option entity.
      */
    def value: SelectionBuilder[SastCiConfigurationOptionsEntity, Option[String]] = Field("value", OptionOf(Scalar()))
  }

  type SastCiConfigurationOptionsEntityConnection

  object SastCiConfigurationOptionsEntityConnection {

    /**
      * A list of edges.
      */
    def edges[A](
      innerSelection: SelectionBuilder[SastCiConfigurationOptionsEntityEdge, A]
    ): SelectionBuilder[SastCiConfigurationOptionsEntityConnection, Option[List[Option[A]]]] =
      Field("edges", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * A list of nodes.
      */
    def nodes[A](
      innerSelection: SelectionBuilder[SastCiConfigurationOptionsEntity, A]
    ): SelectionBuilder[SastCiConfigurationOptionsEntityConnection, Option[List[Option[A]]]] =
      Field("nodes", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * Information to aid in pagination.
      */
    def pageInfo[A](innerSelection: SelectionBuilder[PageInfo, A]): SelectionBuilder[SastCiConfigurationOptionsEntityConnection, A] =
      Field("pageInfo", Obj(innerSelection))
  }

  type SastCiConfigurationOptionsEntityEdge

  object SastCiConfigurationOptionsEntityEdge {

    /**
      * A cursor for use in pagination.
      */
    def cursor: SelectionBuilder[SastCiConfigurationOptionsEntityEdge, String] = Field("cursor", Scalar())

    /**
      * The item at the end of the edge.
      */
    def node[A](
      innerSelection: SelectionBuilder[SastCiConfigurationOptionsEntity, A]
    ): SelectionBuilder[SastCiConfigurationOptionsEntityEdge, Option[A]] = Field("node", OptionOf(Obj(innerSelection)))

  }

  type ScannedResource

  object ScannedResource {

    /**
      * The HTTP request method used to access the URL
      */
    def requestMethod: SelectionBuilder[ScannedResource, Option[String]] = Field("requestMethod", OptionOf(Scalar()))

    /**
      * The URL scanned by the scanner
      */
    def url: SelectionBuilder[ScannedResource, Option[String]] = Field("url", OptionOf(Scalar()))
  }

  type ScannedResourceConnection

  object ScannedResourceConnection {

    /**
      * A list of edges.
      */
    def edges[A](
      innerSelection: SelectionBuilder[ScannedResourceEdge, A]
    ): SelectionBuilder[ScannedResourceConnection, Option[List[Option[A]]]] =
      Field("edges", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * A list of nodes.
      */
    def nodes[A](
      innerSelection: SelectionBuilder[ScannedResource, A]
    ): SelectionBuilder[ScannedResourceConnection, Option[List[Option[A]]]] =
      Field("nodes", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * Information to aid in pagination.
      */
    def pageInfo[A](innerSelection: SelectionBuilder[PageInfo, A]): SelectionBuilder[ScannedResourceConnection, A] =
      Field("pageInfo", Obj(innerSelection))
  }

  type ScannedResourceEdge

  object ScannedResourceEdge {

    /**
      * A cursor for use in pagination.
      */
    def cursor: SelectionBuilder[ScannedResourceEdge, String] = Field("cursor", Scalar())

    /**
      * The item at the end of the edge.
      */
    def node[A](innerSelection: SelectionBuilder[ScannedResource, A]): SelectionBuilder[ScannedResourceEdge, Option[A]] =
      Field("node", OptionOf(Obj(innerSelection)))
  }

  type SecurityReportSummary

  object SecurityReportSummary {

    /**
      * Aggregated counts for the container_scanning scan
      */
    def containerScanning[A](
      innerSelection: SelectionBuilder[SecurityReportSummarySection, A]
    ): SelectionBuilder[SecurityReportSummary, Option[A]] = Field("containerScanning", OptionOf(Obj(innerSelection)))

    /**
      * Aggregated counts for the coverage_fuzzing scan
      */
    def coverageFuzzing[A](
      innerSelection: SelectionBuilder[SecurityReportSummarySection, A]
    ): SelectionBuilder[SecurityReportSummary, Option[A]] = Field("coverageFuzzing", OptionOf(Obj(innerSelection)))

    /**
      * Aggregated counts for the dast scan
      */
    def dast[A](innerSelection: SelectionBuilder[SecurityReportSummarySection, A]): SelectionBuilder[SecurityReportSummary, Option[A]] =
      Field("dast", OptionOf(Obj(innerSelection)))

    /**
      * Aggregated counts for the dependency_scanning scan
      */
    def dependencyScanning[A](
      innerSelection: SelectionBuilder[SecurityReportSummarySection, A]
    ): SelectionBuilder[SecurityReportSummary, Option[A]] = Field("dependencyScanning", OptionOf(Obj(innerSelection)))

    /**
      * Aggregated counts for the sast scan
      */
    def sast[A](innerSelection: SelectionBuilder[SecurityReportSummarySection, A]): SelectionBuilder[SecurityReportSummary, Option[A]] =
      Field("sast", OptionOf(Obj(innerSelection)))

    /**
      * Aggregated counts for the secret_detection scan
      */
    def secretDetection[A](
      innerSelection: SelectionBuilder[SecurityReportSummarySection, A]
    ): SelectionBuilder[SecurityReportSummary, Option[A]] = Field("secretDetection", OptionOf(Obj(innerSelection)))

  }

  type SecurityReportSummarySection

  object SecurityReportSummarySection {

    /**
      * A list of the first 20 scanned resources
      */
    def scannedResources[A](
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[ScannedResourceConnection, A]
    ): SelectionBuilder[SecurityReportSummarySection, Option[A]] =
      Field(
        "scannedResources",
        OptionOf(Obj(innerSelection)),
        arguments = List(Argument("after", after), Argument("before", before), Argument("first", first), Argument("last", last))
      )

    /**
      * Total number of scanned resources
      */
    def scannedResourcesCount: SelectionBuilder[SecurityReportSummarySection, Option[Int]] =
      Field("scannedResourcesCount", OptionOf(Scalar()))

    /**
      * Path to download all the scanned resources in CSV format
      */
    def scannedResourcesCsvPath: SelectionBuilder[SecurityReportSummarySection, Option[String]] =
      Field("scannedResourcesCsvPath", OptionOf(Scalar()))

    /**
      * Total number of vulnerabilities
      */
    def vulnerabilitiesCount: SelectionBuilder[SecurityReportSummarySection, Option[Int]] =
      Field("vulnerabilitiesCount", OptionOf(Scalar()))
  }

  type SecurityScanners

  object SecurityScanners {

    /**
      * List of analyzers which are available for the project.
      */
    def available: SelectionBuilder[SecurityScanners, Option[List[SecurityScannerType]]] = Field("available", OptionOf(ListOf(Scalar())))

    /**
      * List of analyzers which are enabled for the project.
      */
    def enabled: SelectionBuilder[SecurityScanners, Option[List[SecurityScannerType]]] = Field("enabled", OptionOf(ListOf(Scalar())))

    /**
      * List of analyzers which ran successfully in the latest pipeline.
      */
    def pipelineRun: SelectionBuilder[SecurityScanners, Option[List[SecurityScannerType]]] =
      Field("pipelineRun", OptionOf(ListOf(Scalar())))
  }

  type SentryDetailedError

  object SentryDetailedError {

    /**
      * Count of occurrences
      */
    def count: SelectionBuilder[SentryDetailedError, Int] = Field("count", Scalar())

    /**
      * Culprit of the error
      */
    def culprit: SelectionBuilder[SentryDetailedError, String] = Field("culprit", Scalar())

    /**
      * External Base URL of the Sentry Instance
      */
    def externalBaseUrl: SelectionBuilder[SentryDetailedError, String] = Field("externalBaseUrl", Scalar())

    /**
      * External URL of the error
      */
    def externalUrl: SelectionBuilder[SentryDetailedError, String] = Field("externalUrl", Scalar())

    /**
      * Commit the error was first seen
      */
    def firstReleaseLastCommit: SelectionBuilder[SentryDetailedError, Option[String]] = Field("firstReleaseLastCommit", OptionOf(Scalar()))

    /**
      * Release short version the error was first seen
      */
    def firstReleaseShortVersion: SelectionBuilder[SentryDetailedError, Option[String]] =
      Field("firstReleaseShortVersion", OptionOf(Scalar()))

    /**
      * Release version the error was first seen
      */
    def firstReleaseVersion: SelectionBuilder[SentryDetailedError, Option[String]] = Field("firstReleaseVersion", OptionOf(Scalar()))

    /**
      * Timestamp when the error was first seen
      */
    def firstSeen: SelectionBuilder[SentryDetailedError, Time] = Field("firstSeen", Scalar())

    /**
      * Last 24hr stats of the error
      */
    def frequency[A](innerSelection: SelectionBuilder[SentryErrorFrequency, A]): SelectionBuilder[SentryDetailedError, List[A]] =
      Field("frequency", ListOf(Obj(innerSelection)))

    /**
      * GitLab commit SHA attributed to the Error based on the release version
      */
    def gitlabCommit: SelectionBuilder[SentryDetailedError, Option[String]] = Field("gitlabCommit", OptionOf(Scalar()))

    /**
      * Path to the GitLab page for the GitLab commit attributed to the error
      */
    def gitlabCommitPath: SelectionBuilder[SentryDetailedError, Option[String]] = Field("gitlabCommitPath", OptionOf(Scalar()))

    /**
      * URL of GitLab Issue
      */
    def gitlabIssuePath: SelectionBuilder[SentryDetailedError, Option[String]] = Field("gitlabIssuePath", OptionOf(Scalar()))

    /**
      * ID (global ID) of the error
      */
    def id: SelectionBuilder[SentryDetailedError, String] = Field("id", Scalar())

    /**
      * Commit the error was last seen
      */
    def lastReleaseLastCommit: SelectionBuilder[SentryDetailedError, Option[String]] = Field("lastReleaseLastCommit", OptionOf(Scalar()))

    /**
      * Release short version the error was last seen
      */
    def lastReleaseShortVersion: SelectionBuilder[SentryDetailedError, Option[String]] =
      Field("lastReleaseShortVersion", OptionOf(Scalar()))

    /**
      * Release version the error was last seen
      */
    def lastReleaseVersion: SelectionBuilder[SentryDetailedError, Option[String]] = Field("lastReleaseVersion", OptionOf(Scalar()))

    /**
      * Timestamp when the error was last seen
      */
    def lastSeen: SelectionBuilder[SentryDetailedError, Time] = Field("lastSeen", Scalar())

    /**
      * Sentry metadata message of the error
      */
    def message: SelectionBuilder[SentryDetailedError, Option[String]] = Field("message", OptionOf(Scalar()))

    /**
      * ID (Sentry ID) of the error
      */
    def sentryId: SelectionBuilder[SentryDetailedError, String] = Field("sentryId", Scalar())

    /**
      * ID of the project (Sentry project)
      */
    def sentryProjectId: SelectionBuilder[SentryDetailedError, String] = Field("sentryProjectId", Scalar())

    /**
      * Name of the project affected by the error
      */
    def sentryProjectName: SelectionBuilder[SentryDetailedError, String] = Field("sentryProjectName", Scalar())

    /**
      * Slug of the project affected by the error
      */
    def sentryProjectSlug: SelectionBuilder[SentryDetailedError, String] = Field("sentryProjectSlug", Scalar())

    /**
      * Short ID (Sentry ID) of the error
      */
    def shortId: SelectionBuilder[SentryDetailedError, String] = Field("shortId", Scalar())

    /**
      * Status of the error
      */
    def status: SelectionBuilder[SentryDetailedError, SentryErrorStatus] = Field("status", Scalar())

    /**
      * Tags associated with the Sentry Error
      */
    def tags[A](innerSelection: SelectionBuilder[SentryErrorTags, A]): SelectionBuilder[SentryDetailedError, A] =
      Field("tags", Obj(innerSelection))

    /**
      * Title of the error
      */
    def title: SelectionBuilder[SentryDetailedError, String] = Field("title", Scalar())

    /**
      * Type of the error
      */
    def `type`: SelectionBuilder[SentryDetailedError, String] = Field("type", Scalar())

    /**
      * Count of users affected by the error
      */
    def userCount: SelectionBuilder[SentryDetailedError, Int] = Field("userCount", Scalar())
  }

  type SentryError

  object SentryError {

    /**
      * Count of occurrences
      */
    def count: SelectionBuilder[SentryError, Int] = Field("count", Scalar())

    /**
      * Culprit of the error
      */
    def culprit: SelectionBuilder[SentryError, String] = Field("culprit", Scalar())

    /**
      * External URL of the error
      */
    def externalUrl: SelectionBuilder[SentryError, String] = Field("externalUrl", Scalar())

    /**
      * Timestamp when the error was first seen
      */
    def firstSeen: SelectionBuilder[SentryError, Time] = Field("firstSeen", Scalar())

    /**
      * Last 24hr stats of the error
      */
    def frequency[A](innerSelection: SelectionBuilder[SentryErrorFrequency, A]): SelectionBuilder[SentryError, List[A]] =
      Field("frequency", ListOf(Obj(innerSelection)))

    /**
      * ID (global ID) of the error
      */
    def id: SelectionBuilder[SentryError, String] = Field("id", Scalar())

    /**
      * Timestamp when the error was last seen
      */
    def lastSeen: SelectionBuilder[SentryError, Time] = Field("lastSeen", Scalar())

    /**
      * Sentry metadata message of the error
      */
    def message: SelectionBuilder[SentryError, Option[String]] = Field("message", OptionOf(Scalar()))

    /**
      * ID (Sentry ID) of the error
      */
    def sentryId: SelectionBuilder[SentryError, String] = Field("sentryId", Scalar())

    /**
      * ID of the project (Sentry project)
      */
    def sentryProjectId: SelectionBuilder[SentryError, String] = Field("sentryProjectId", Scalar())

    /**
      * Name of the project affected by the error
      */
    def sentryProjectName: SelectionBuilder[SentryError, String] = Field("sentryProjectName", Scalar())

    /**
      * Slug of the project affected by the error
      */
    def sentryProjectSlug: SelectionBuilder[SentryError, String] = Field("sentryProjectSlug", Scalar())

    /**
      * Short ID (Sentry ID) of the error
      */
    def shortId: SelectionBuilder[SentryError, String] = Field("shortId", Scalar())

    /**
      * Status of the error
      */
    def status: SelectionBuilder[SentryError, SentryErrorStatus] = Field("status", Scalar())

    /**
      * Title of the error
      */
    def title: SelectionBuilder[SentryError, String] = Field("title", Scalar())

    /**
      * Type of the error
      */
    def `type`: SelectionBuilder[SentryError, String] = Field("type", Scalar())

    /**
      * Count of users affected by the error
      */
    def userCount: SelectionBuilder[SentryError, Int] = Field("userCount", Scalar())
  }

  type SentryErrorCollection

  object SentryErrorCollection {

    /**
      * Detailed version of a Sentry error on the project
      */
    def detailedError[A](
      id: String
    )(
      innerSelection: SelectionBuilder[SentryDetailedError, A]
    ): SelectionBuilder[SentryErrorCollection, Option[A]] =
      Field("detailedError", OptionOf(Obj(innerSelection)), arguments = List(Argument("id", id)))

    /**
      * Stack Trace of Sentry Error
      */
    def errorStackTrace[A](
      id: String
    )(
      innerSelection: SelectionBuilder[SentryErrorStackTrace, A]
    ): SelectionBuilder[SentryErrorCollection, Option[A]] =
      Field("errorStackTrace", OptionOf(Obj(innerSelection)), arguments = List(Argument("id", id)))

    /**
      * Collection of Sentry Errors
      */
    def errors[A](
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None,
      searchTerm: Option[String] = None,
      sort: Option[String] = None
    )(
      innerSelection: SelectionBuilder[SentryErrorConnection, A]
    ): SelectionBuilder[SentryErrorCollection, Option[A]] =
      Field(
        "errors",
        OptionOf(Obj(innerSelection)),
        arguments = List(
          Argument("after", after),
          Argument("before", before),
          Argument("first", first),
          Argument("last", last),
          Argument("searchTerm", searchTerm),
          Argument("sort", sort)
        )
      )

    /**
      * External URL for Sentry
      */
    def externalUrl: SelectionBuilder[SentryErrorCollection, Option[String]] = Field("externalUrl", OptionOf(Scalar()))
  }

  type SentryErrorConnection

  object SentryErrorConnection {

    /**
      * A list of edges.
      */
    def edges[A](innerSelection: SelectionBuilder[SentryErrorEdge, A]): SelectionBuilder[SentryErrorConnection, Option[List[Option[A]]]] =
      Field("edges", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * A list of nodes.
      */
    def nodes[A](innerSelection: SelectionBuilder[SentryError, A]): SelectionBuilder[SentryErrorConnection, Option[List[Option[A]]]] =
      Field("nodes", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * Information to aid in pagination.
      */
    def pageInfo[A](innerSelection: SelectionBuilder[PageInfo, A]): SelectionBuilder[SentryErrorConnection, A] =
      Field("pageInfo", Obj(innerSelection))
  }

  type SentryErrorEdge

  object SentryErrorEdge {

    /**
      * A cursor for use in pagination.
      */
    def cursor: SelectionBuilder[SentryErrorEdge, String] = Field("cursor", Scalar())

    /**
      * The item at the end of the edge.
      */
    def node[A](innerSelection: SelectionBuilder[SentryError, A]): SelectionBuilder[SentryErrorEdge, Option[A]] =
      Field("node", OptionOf(Obj(innerSelection)))
  }

  type SentryErrorFrequency

  object SentryErrorFrequency {

    /**
      * Count of errors received since the previously recorded time
      */
    def count: SelectionBuilder[SentryErrorFrequency, Int] = Field("count", Scalar())

    /**
      * Time the error frequency stats were recorded
      */
    def time: SelectionBuilder[SentryErrorFrequency, Time] = Field("time", Scalar())
  }

  type SentryErrorStackTrace

  object SentryErrorStackTrace {

    /**
      * Time the stack trace was received by Sentry
      */
    def dateReceived: SelectionBuilder[SentryErrorStackTrace, String] = Field("dateReceived", Scalar())

    /**
      * ID of the Sentry error
      */
    def issueId: SelectionBuilder[SentryErrorStackTrace, String] = Field("issueId", Scalar())

    /**
      * Stack trace entries for the Sentry error
      */
    def stackTraceEntries[A](
      innerSelection: SelectionBuilder[SentryErrorStackTraceEntry, A]
    ): SelectionBuilder[SentryErrorStackTrace, List[A]] = Field("stackTraceEntries", ListOf(Obj(innerSelection)))

  }

  type SentryErrorStackTraceContext

  object SentryErrorStackTraceContext {

    /**
      * Code number of the context
      */
    def code: SelectionBuilder[SentryErrorStackTraceContext, String] = Field("code", Scalar())

    /**
      * Line number of the context
      */
    def line: SelectionBuilder[SentryErrorStackTraceContext, Int] = Field("line", Scalar())
  }

  type SentryErrorStackTraceEntry

  object SentryErrorStackTraceEntry {

    /**
      * Function in which the Sentry error occurred
      */
    def col: SelectionBuilder[SentryErrorStackTraceEntry, Option[String]] = Field("col", OptionOf(Scalar()))

    /**
      * File in which the Sentry error occurred
      */
    def fileName: SelectionBuilder[SentryErrorStackTraceEntry, Option[String]] = Field("fileName", OptionOf(Scalar()))

    /**
      * Function in which the Sentry error occurred
      */
    def function: SelectionBuilder[SentryErrorStackTraceEntry, Option[String]] = Field("function", OptionOf(Scalar()))

    /**
      * Function in which the Sentry error occurred
      */
    def line: SelectionBuilder[SentryErrorStackTraceEntry, Option[String]] = Field("line", OptionOf(Scalar()))

    /**
      * Context of the Sentry error
      */
    def traceContext[A](
      innerSelection: SelectionBuilder[SentryErrorStackTraceContext, A]
    ): SelectionBuilder[SentryErrorStackTraceEntry, Option[List[A]]] = Field("traceContext", OptionOf(ListOf(Obj(innerSelection))))

  }

  type SentryErrorTags

  object SentryErrorTags {

    /**
      * Severity level of the Sentry Error
      */
    def level: SelectionBuilder[SentryErrorTags, Option[String]] = Field("level", OptionOf(Scalar()))

    /**
      * Logger of the Sentry Error
      */
    def logger: SelectionBuilder[SentryErrorTags, Option[String]] = Field("logger", OptionOf(Scalar()))
  }

  type ServiceConnection

  object ServiceConnection {

    /**
      * A list of edges.
      */
    def edges[A](innerSelection: SelectionBuilder[ServiceEdge, A]): SelectionBuilder[ServiceConnection, Option[List[Option[A]]]] =
      Field("edges", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * A list of nodes.
      */
    def nodes[A](
      onBaseService: Option[SelectionBuilder[BaseService, A]] = None,
      onJiraService: Option[SelectionBuilder[JiraService, A]] = None
    ): SelectionBuilder[ServiceConnection, Option[List[Option[A]]]] =
      Field(
        "nodes",
        OptionOf(ListOf(OptionOf(ChoiceOf(Map("BaseService" -> onBaseService, "JiraService" -> onJiraService).collect {
          case (k, Some(v)) => k -> Obj(v)
        }))))
      )

    /**
      * Information to aid in pagination.
      */
    def pageInfo[A](innerSelection: SelectionBuilder[PageInfo, A]): SelectionBuilder[ServiceConnection, A] =
      Field("pageInfo", Obj(innerSelection))
  }

  type ServiceEdge

  object ServiceEdge {

    /**
      * A cursor for use in pagination.
      */
    def cursor: SelectionBuilder[ServiceEdge, String] = Field("cursor", Scalar())

    /**
      * The item at the end of the edge.
      */
    def node[A](
      onBaseService: Option[SelectionBuilder[BaseService, A]] = None,
      onJiraService: Option[SelectionBuilder[JiraService, A]] = None
    ): SelectionBuilder[ServiceEdge, Option[A]] =
      Field(
        "node",
        OptionOf(ChoiceOf(Map("BaseService" -> onBaseService, "JiraService" -> onJiraService).collect { case (k, Some(v)) => k -> Obj(v) }))
      )

  }

  type Snippet

  object Snippet {

    /**
      * The owner of the snippet
      */
    def author[A](innerSelection: SelectionBuilder[User, A]): SelectionBuilder[Snippet, Option[A]] =
      Field("author", OptionOf(Obj(innerSelection)))

    /**
      * Snippet blob
      */
    def blob[A](innerSelection: SelectionBuilder[SnippetBlob, A]): SelectionBuilder[Snippet, A] = Field("blob", Obj(innerSelection))

    /**
      * Snippet blobs
      */
    def blobs[A](innerSelection: SelectionBuilder[SnippetBlob, A]): SelectionBuilder[Snippet, List[A]] =
      Field("blobs", ListOf(Obj(innerSelection)))

    /**
      * Timestamp this snippet was created
      */
    def createdAt: SelectionBuilder[Snippet, Time] = Field("createdAt", Scalar())

    /**
      * Description of the snippet
      */
    def description: SelectionBuilder[Snippet, Option[String]] = Field("description", OptionOf(Scalar()))

    /**
      * The GitLab Flavored Markdown rendering of `description`
      */
    def descriptionHtml: SelectionBuilder[Snippet, Option[String]] = Field("descriptionHtml", OptionOf(Scalar()))

    /**
      * All discussions on this noteable
      */
    def discussions[A](
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[DiscussionConnection, A]
    ): SelectionBuilder[Snippet, A] =
      Field(
        "discussions",
        Obj(innerSelection),
        arguments = List(Argument("after", after), Argument("before", before), Argument("first", first), Argument("last", last))
      )

    /**
      * File Name of the snippet
      */
    def fileName: SelectionBuilder[Snippet, Option[String]] = Field("fileName", OptionOf(Scalar()))

    /**
      * HTTP URL to the snippet repository
      */
    def httpUrlToRepo: SelectionBuilder[Snippet, Option[String]] = Field("httpUrlToRepo", OptionOf(Scalar()))

    /**
      * ID of the snippet
      */
    def id: SelectionBuilder[Snippet, String] = Field("id", Scalar())

    /**
      * All notes on this noteable
      */
    def notes[A](
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[NoteConnection, A]
    ): SelectionBuilder[Snippet, A] =
      Field(
        "notes",
        Obj(innerSelection),
        arguments = List(Argument("after", after), Argument("before", before), Argument("first", first), Argument("last", last))
      )

    /**
      * The project the snippet is associated with
      */
    def project[A](innerSelection: SelectionBuilder[Project, A]): SelectionBuilder[Snippet, Option[A]] =
      Field("project", OptionOf(Obj(innerSelection)))

    /**
      * Raw URL of the snippet
      */
    def rawUrl: SelectionBuilder[Snippet, String] = Field("rawUrl", Scalar())

    /**
      * SSH URL to the snippet repository
      */
    def sshUrlToRepo: SelectionBuilder[Snippet, Option[String]] = Field("sshUrlToRepo", OptionOf(Scalar()))

    /**
      * Title of the snippet
      */
    def title: SelectionBuilder[Snippet, String] = Field("title", Scalar())

    /**
      * Timestamp this snippet was updated
      */
    def updatedAt: SelectionBuilder[Snippet, Time] = Field("updatedAt", Scalar())

    /**
      * Permissions for the current user on the resource
      */
    def userPermissions[A](innerSelection: SelectionBuilder[SnippetPermissions, A]): SelectionBuilder[Snippet, A] =
      Field("userPermissions", Obj(innerSelection))

    /**
      * Visibility Level of the snippet
      */
    def visibilityLevel: SelectionBuilder[Snippet, VisibilityLevelsEnum] = Field("visibilityLevel", Scalar())

    /**
      * Web URL of the snippet
      */
    def webUrl: SelectionBuilder[Snippet, String] = Field("webUrl", Scalar())
  }

  type SnippetBlob

  object SnippetBlob {

    /**
      * Shows whether the blob is binary
      */
    def binary: SelectionBuilder[SnippetBlob, Boolean] = Field("binary", Scalar())

    /**
      * Blob external storage
      */
    def externalStorage: SelectionBuilder[SnippetBlob, Option[String]] = Field("externalStorage", OptionOf(Scalar()))

    /**
      * Blob mode
      */
    def mode: SelectionBuilder[SnippetBlob, Option[String]] = Field("mode", OptionOf(Scalar()))

    /**
      * Blob name
      */
    def name: SelectionBuilder[SnippetBlob, Option[String]] = Field("name", OptionOf(Scalar()))

    /**
      * Blob path
      */
    def path: SelectionBuilder[SnippetBlob, Option[String]] = Field("path", OptionOf(Scalar()))

    /**
      * Blob plain highlighted data
      */
    def plainData: SelectionBuilder[SnippetBlob, Option[String]] = Field("plainData", OptionOf(Scalar()))

    /**
      * Blob raw content endpoint path
      */
    def rawPath: SelectionBuilder[SnippetBlob, String] = Field("rawPath", Scalar())

    /**
      * Shows whether the blob is rendered as text
      */
    def renderedAsText: SelectionBuilder[SnippetBlob, Boolean] = Field("renderedAsText", Scalar())

    /**
      * Blob highlighted data
      */
    def richData: SelectionBuilder[SnippetBlob, Option[String]] = Field("richData", OptionOf(Scalar()))

    /**
      * Blob content rich viewer
      */
    def richViewer[A](innerSelection: SelectionBuilder[SnippetBlobViewer, A]): SelectionBuilder[SnippetBlob, Option[A]] =
      Field("richViewer", OptionOf(Obj(innerSelection)))

    /**
      * Blob content simple viewer
      */
    def simpleViewer[A](innerSelection: SelectionBuilder[SnippetBlobViewer, A]): SelectionBuilder[SnippetBlob, A] =
      Field("simpleViewer", Obj(innerSelection))

    /**
      * Blob size
      */
    def size: SelectionBuilder[SnippetBlob, Int] = Field("size", Scalar())
  }

  type SnippetBlobViewer

  object SnippetBlobViewer {

    /**
      * Shows whether the blob should be displayed collapsed
      */
    def collapsed: SelectionBuilder[SnippetBlobViewer, Boolean] = Field("collapsed", Scalar())

    /**
      * Content file type
      */
    def fileType: SelectionBuilder[SnippetBlobViewer, String] = Field("fileType", Scalar())

    /**
      * Shows whether the blob content is loaded async
      */
    def loadAsync: SelectionBuilder[SnippetBlobViewer, Boolean] = Field("loadAsync", Scalar())

    /**
      * Loading partial name
      */
    def loadingPartialName: SelectionBuilder[SnippetBlobViewer, String] = Field("loadingPartialName", Scalar())

    /**
      * Error rendering the blob content
      */
    def renderError: SelectionBuilder[SnippetBlobViewer, Option[String]] = Field("renderError", OptionOf(Scalar()))

    /**
      * Shows whether the blob too large to be displayed
      */
    def tooLarge: SelectionBuilder[SnippetBlobViewer, Boolean] = Field("tooLarge", Scalar())

    /**
      * Type of blob viewer
      */
    def `type`: SelectionBuilder[SnippetBlobViewer, BlobViewersType] = Field("type", Scalar())
  }

  type SnippetConnection

  object SnippetConnection {

    /**
      * A list of edges.
      */
    def edges[A](innerSelection: SelectionBuilder[SnippetEdge, A]): SelectionBuilder[SnippetConnection, Option[List[Option[A]]]] =
      Field("edges", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * A list of nodes.
      */
    def nodes[A](innerSelection: SelectionBuilder[Snippet, A]): SelectionBuilder[SnippetConnection, Option[List[Option[A]]]] =
      Field("nodes", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * Information to aid in pagination.
      */
    def pageInfo[A](innerSelection: SelectionBuilder[PageInfo, A]): SelectionBuilder[SnippetConnection, A] =
      Field("pageInfo", Obj(innerSelection))
  }

  type SnippetEdge

  object SnippetEdge {

    /**
      * A cursor for use in pagination.
      */
    def cursor: SelectionBuilder[SnippetEdge, String] = Field("cursor", Scalar())

    /**
      * The item at the end of the edge.
      */
    def node[A](innerSelection: SelectionBuilder[Snippet, A]): SelectionBuilder[SnippetEdge, Option[A]] =
      Field("node", OptionOf(Obj(innerSelection)))
  }

  type SnippetPermissions

  object SnippetPermissions {

    /**
      * Indicates the user can perform `admin_snippet` on this resource
      */
    def adminSnippet: SelectionBuilder[SnippetPermissions, Boolean] = Field("adminSnippet", Scalar())

    /**
      * Indicates the user can perform `award_emoji` on this resource
      */
    def awardEmoji: SelectionBuilder[SnippetPermissions, Boolean] = Field("awardEmoji", Scalar())

    /**
      * Indicates the user can perform `create_note` on this resource
      */
    def createNote: SelectionBuilder[SnippetPermissions, Boolean] = Field("createNote", Scalar())

    /**
      * Indicates the user can perform `read_snippet` on this resource
      */
    def readSnippet: SelectionBuilder[SnippetPermissions, Boolean] = Field("readSnippet", Scalar())

    /**
      * Indicates the user can perform `report_snippet` on this resource
      */
    def reportSnippet: SelectionBuilder[SnippetPermissions, Boolean] = Field("reportSnippet", Scalar())

    /**
      * Indicates the user can perform `update_snippet` on this resource
      */
    def updateSnippet: SelectionBuilder[SnippetPermissions, Boolean] = Field("updateSnippet", Scalar())
  }

  type Submodule

  object Submodule {

    /**
      * Flat path of the entry
      */
    def flatPath: SelectionBuilder[Submodule, String] = Field("flatPath", Scalar())

    /**
      * ID of the entry
      */
    def id: SelectionBuilder[Submodule, String] = Field("id", Scalar())

    /**
      * Name of the entry
      */
    def name: SelectionBuilder[Submodule, String] = Field("name", Scalar())

    /**
      * Path of the entry
      */
    def path: SelectionBuilder[Submodule, String] = Field("path", Scalar())

    /**
      * Last commit sha for the entry
      */
    def sha: SelectionBuilder[Submodule, String] = Field("sha", Scalar())

    /**
      * Tree URL for the sub-module
      */
    def treeUrl: SelectionBuilder[Submodule, Option[String]] = Field("treeUrl", OptionOf(Scalar()))

    /**
      * Type of tree entry
      */
    def `type`: SelectionBuilder[Submodule, EntryType] = Field("type", Scalar())

    /**
      * Web URL for the sub-module
      */
    def webUrl: SelectionBuilder[Submodule, Option[String]] = Field("webUrl", OptionOf(Scalar()))
  }

  type SubmoduleConnection

  object SubmoduleConnection {

    /**
      * A list of edges.
      */
    def edges[A](innerSelection: SelectionBuilder[SubmoduleEdge, A]): SelectionBuilder[SubmoduleConnection, Option[List[Option[A]]]] =
      Field("edges", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * A list of nodes.
      */
    def nodes[A](innerSelection: SelectionBuilder[Submodule, A]): SelectionBuilder[SubmoduleConnection, Option[List[Option[A]]]] =
      Field("nodes", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * Information to aid in pagination.
      */
    def pageInfo[A](innerSelection: SelectionBuilder[PageInfo, A]): SelectionBuilder[SubmoduleConnection, A] =
      Field("pageInfo", Obj(innerSelection))
  }

  type SubmoduleEdge

  object SubmoduleEdge {

    /**
      * A cursor for use in pagination.
      */
    def cursor: SelectionBuilder[SubmoduleEdge, String] = Field("cursor", Scalar())

    /**
      * The item at the end of the edge.
      */
    def node[A](innerSelection: SelectionBuilder[Submodule, A]): SelectionBuilder[SubmoduleEdge, Option[A]] =
      Field("node", OptionOf(Obj(innerSelection)))
  }

  type TaskCompletionStatus

  object TaskCompletionStatus {

    /**
      * Number of completed tasks
      */
    def completedCount: SelectionBuilder[TaskCompletionStatus, Int] = Field("completedCount", Scalar())

    /**
      * Number of total tasks
      */
    def count: SelectionBuilder[TaskCompletionStatus, Int] = Field("count", Scalar())
  }

  type TestReport

  object TestReport {

    /**
      * Author of the test report
      */
    def author[A](innerSelection: SelectionBuilder[User, A]): SelectionBuilder[TestReport, Option[A]] =
      Field("author", OptionOf(Obj(innerSelection)))

    /**
      * Timestamp of when the test report was created
      */
    def createdAt: SelectionBuilder[TestReport, Time] = Field("createdAt", Scalar())

    /**
      * ID of the test report
      */
    def id: SelectionBuilder[TestReport, String] = Field("id", Scalar())

    /**
      * State of the test report
      */
    def state: SelectionBuilder[TestReport, TestReportState] = Field("state", Scalar())
  }

  type TestReportConnection

  object TestReportConnection {

    /**
      * A list of edges.
      */
    def edges[A](innerSelection: SelectionBuilder[TestReportEdge, A]): SelectionBuilder[TestReportConnection, Option[List[Option[A]]]] =
      Field("edges", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * A list of nodes.
      */
    def nodes[A](innerSelection: SelectionBuilder[TestReport, A]): SelectionBuilder[TestReportConnection, Option[List[Option[A]]]] =
      Field("nodes", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * Information to aid in pagination.
      */
    def pageInfo[A](innerSelection: SelectionBuilder[PageInfo, A]): SelectionBuilder[TestReportConnection, A] =
      Field("pageInfo", Obj(innerSelection))
  }

  type TestReportEdge

  object TestReportEdge {

    /**
      * A cursor for use in pagination.
      */
    def cursor: SelectionBuilder[TestReportEdge, String] = Field("cursor", Scalar())

    /**
      * The item at the end of the edge.
      */
    def node[A](innerSelection: SelectionBuilder[TestReport, A]): SelectionBuilder[TestReportEdge, Option[A]] =
      Field("node", OptionOf(Obj(innerSelection)))
  }

  type Timelog

  object Timelog {

    /**
      * Timestamp of when the time tracked was spent at. Deprecated in 12.10: Use `spentAt`
      */
    @deprecated("Use `spentAt`. Deprecated in 12.10", "")
    def date: SelectionBuilder[Timelog, Time] = Field("date", Scalar())

    /**
      * The issue that logged time was added to
      */
    def issue[A](innerSelection: SelectionBuilder[Issue, A]): SelectionBuilder[Timelog, Option[A]] =
      Field("issue", OptionOf(Obj(innerSelection)))

    /**
      * Timestamp of when the time tracked was spent at
      */
    def spentAt: SelectionBuilder[Timelog, Option[Time]] = Field("spentAt", OptionOf(Scalar()))

    /**
      * The time spent displayed in seconds
      */
    def timeSpent: SelectionBuilder[Timelog, Int] = Field("timeSpent", Scalar())

    /**
      * The user that logged the time
      */
    def user[A](innerSelection: SelectionBuilder[User, A]): SelectionBuilder[Timelog, A] = Field("user", Obj(innerSelection))
  }

  type TimelogConnection

  object TimelogConnection {

    /**
      * A list of edges.
      */
    def edges[A](innerSelection: SelectionBuilder[TimelogEdge, A]): SelectionBuilder[TimelogConnection, Option[List[Option[A]]]] =
      Field("edges", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * A list of nodes.
      */
    def nodes[A](innerSelection: SelectionBuilder[Timelog, A]): SelectionBuilder[TimelogConnection, Option[List[Option[A]]]] =
      Field("nodes", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * Information to aid in pagination.
      */
    def pageInfo[A](innerSelection: SelectionBuilder[PageInfo, A]): SelectionBuilder[TimelogConnection, A] =
      Field("pageInfo", Obj(innerSelection))
  }

  type TimelogEdge

  object TimelogEdge {

    /**
      * A cursor for use in pagination.
      */
    def cursor: SelectionBuilder[TimelogEdge, String] = Field("cursor", Scalar())

    /**
      * The item at the end of the edge.
      */
    def node[A](innerSelection: SelectionBuilder[Timelog, A]): SelectionBuilder[TimelogEdge, Option[A]] =
      Field("node", OptionOf(Obj(innerSelection)))
  }

  type Todo

  object Todo {

    /**
      * Action of the todo
      */
    def action: SelectionBuilder[Todo, TodoActionEnum] = Field("action", Scalar())

    /**
      * The owner of this todo
      */
    def author[A](innerSelection: SelectionBuilder[User, A]): SelectionBuilder[Todo, A] = Field("author", Obj(innerSelection))

    /**
      * Body of the todo
      */
    def body: SelectionBuilder[Todo, String] = Field("body", Scalar())

    /**
      * Timestamp this todo was created
      */
    def createdAt: SelectionBuilder[Todo, Time] = Field("createdAt", Scalar())

    /**
      * Group this todo is associated with
      */
    def group[A](innerSelection: SelectionBuilder[Group, A]): SelectionBuilder[Todo, Option[A]] =
      Field("group", OptionOf(Obj(innerSelection)))

    /**
      * ID of the todo
      */
    def id: SelectionBuilder[Todo, String] = Field("id", Scalar())

    /**
      * The project this todo is associated with
      */
    def project[A](innerSelection: SelectionBuilder[Project, A]): SelectionBuilder[Todo, Option[A]] =
      Field("project", OptionOf(Obj(innerSelection)))

    /**
      * State of the todo
      */
    def state: SelectionBuilder[Todo, TodoStateEnum] = Field("state", Scalar())

    /**
      * Target type of the todo
      */
    def targetType: SelectionBuilder[Todo, TodoTargetEnum] = Field("targetType", Scalar())
  }

  type TodoConnection

  object TodoConnection {

    /**
      * A list of edges.
      */
    def edges[A](innerSelection: SelectionBuilder[TodoEdge, A]): SelectionBuilder[TodoConnection, Option[List[Option[A]]]] =
      Field("edges", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * A list of nodes.
      */
    def nodes[A](innerSelection: SelectionBuilder[Todo, A]): SelectionBuilder[TodoConnection, Option[List[Option[A]]]] =
      Field("nodes", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * Information to aid in pagination.
      */
    def pageInfo[A](innerSelection: SelectionBuilder[PageInfo, A]): SelectionBuilder[TodoConnection, A] =
      Field("pageInfo", Obj(innerSelection))
  }

  type TodoEdge

  object TodoEdge {

    /**
      * A cursor for use in pagination.
      */
    def cursor: SelectionBuilder[TodoEdge, String] = Field("cursor", Scalar())

    /**
      * The item at the end of the edge.
      */
    def node[A](innerSelection: SelectionBuilder[Todo, A]): SelectionBuilder[TodoEdge, Option[A]] =
      Field("node", OptionOf(Obj(innerSelection)))
  }

  type TodoMarkDonePayload

  object TodoMarkDonePayload {

    /**
      * A unique identifier for the client performing the mutation.
      */
    def clientMutationId: SelectionBuilder[TodoMarkDonePayload, Option[String]] = Field("clientMutationId", OptionOf(Scalar()))

    /**
      * Errors encountered during execution of the mutation.
      */
    def errors: SelectionBuilder[TodoMarkDonePayload, List[String]] = Field("errors", ListOf(Scalar()))

    /**
      * The requested todo
      */
    def todo[A](innerSelection: SelectionBuilder[Todo, A]): SelectionBuilder[TodoMarkDonePayload, A] = Field("todo", Obj(innerSelection))
  }

  type TodoRestoreManyPayload

  object TodoRestoreManyPayload {

    /**
      * A unique identifier for the client performing the mutation.
      */
    def clientMutationId: SelectionBuilder[TodoRestoreManyPayload, Option[String]] = Field("clientMutationId", OptionOf(Scalar()))

    /**
      * Errors encountered during execution of the mutation.
      */
    def errors: SelectionBuilder[TodoRestoreManyPayload, List[String]] = Field("errors", ListOf(Scalar()))

    /**
      * Updated todos
      */
    def todos[A](innerSelection: SelectionBuilder[Todo, A]): SelectionBuilder[TodoRestoreManyPayload, List[A]] =
      Field("todos", ListOf(Obj(innerSelection)))

    /**
      * The ids of the updated todo items. Deprecated in 13.2: Use todos
      */
    @deprecated("Use todos. Deprecated in 13.2", "")
    def updatedIds: SelectionBuilder[TodoRestoreManyPayload, List[String]] = Field("updatedIds", ListOf(Scalar()))
  }

  type TodoRestorePayload

  object TodoRestorePayload {

    /**
      * A unique identifier for the client performing the mutation.
      */
    def clientMutationId: SelectionBuilder[TodoRestorePayload, Option[String]] = Field("clientMutationId", OptionOf(Scalar()))

    /**
      * Errors encountered during execution of the mutation.
      */
    def errors: SelectionBuilder[TodoRestorePayload, List[String]] = Field("errors", ListOf(Scalar()))

    /**
      * The requested todo
      */
    def todo[A](innerSelection: SelectionBuilder[Todo, A]): SelectionBuilder[TodoRestorePayload, A] = Field("todo", Obj(innerSelection))
  }

  type TodosMarkAllDonePayload

  object TodosMarkAllDonePayload {

    /**
      * A unique identifier for the client performing the mutation.
      */
    def clientMutationId: SelectionBuilder[TodosMarkAllDonePayload, Option[String]] = Field("clientMutationId", OptionOf(Scalar()))

    /**
      * Errors encountered during execution of the mutation.
      */
    def errors: SelectionBuilder[TodosMarkAllDonePayload, List[String]] = Field("errors", ListOf(Scalar()))

    /**
      * Updated todos
      */
    def todos[A](innerSelection: SelectionBuilder[Todo, A]): SelectionBuilder[TodosMarkAllDonePayload, List[A]] =
      Field("todos", ListOf(Obj(innerSelection)))

    /**
      * Ids of the updated todos. Deprecated in 13.2: Use todos
      */
    @deprecated("Use todos. Deprecated in 13.2", "")
    def updatedIds: SelectionBuilder[TodosMarkAllDonePayload, List[String]] = Field("updatedIds", ListOf(Scalar()))
  }

  type ToggleAwardEmojiPayload

  object ToggleAwardEmojiPayload {

    /**
      * The award emoji after mutation
      */
    def awardEmoji[A](innerSelection: SelectionBuilder[AwardEmoji, A]): SelectionBuilder[ToggleAwardEmojiPayload, Option[A]] =
      Field("awardEmoji", OptionOf(Obj(innerSelection)))

    /**
      * A unique identifier for the client performing the mutation.
      */
    def clientMutationId: SelectionBuilder[ToggleAwardEmojiPayload, Option[String]] = Field("clientMutationId", OptionOf(Scalar()))

    /**
      * Errors encountered during execution of the mutation.
      */
    def errors: SelectionBuilder[ToggleAwardEmojiPayload, List[String]] = Field("errors", ListOf(Scalar()))

    /**
      * Indicates the status of the emoji. True if the toggle awarded the emoji, and false if the toggle removed the emoji.
      */
    def toggledOn: SelectionBuilder[ToggleAwardEmojiPayload, Boolean] = Field("toggledOn", Scalar())
  }

  type Tree

  object Tree {

    /**
      * Blobs of the tree
      */
    def blobs[A](
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[BlobConnection, A]
    ): SelectionBuilder[Tree, A] =
      Field(
        "blobs",
        Obj(innerSelection),
        arguments = List(Argument("after", after), Argument("before", before), Argument("first", first), Argument("last", last))
      )

    /**
      * Last commit for the tree
      */
    def lastCommit[A](innerSelection: SelectionBuilder[Commit, A]): SelectionBuilder[Tree, Option[A]] =
      Field("lastCommit", OptionOf(Obj(innerSelection)))

    /**
      * Sub-modules of the tree
      */
    def submodules[A](
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[SubmoduleConnection, A]
    ): SelectionBuilder[Tree, A] =
      Field(
        "submodules",
        Obj(innerSelection),
        arguments = List(Argument("after", after), Argument("before", before), Argument("first", first), Argument("last", last))
      )

    /**
      * Trees of the tree
      */
    def trees[A](
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[TreeEntryConnection, A]
    ): SelectionBuilder[Tree, A] =
      Field(
        "trees",
        Obj(innerSelection),
        arguments = List(Argument("after", after), Argument("before", before), Argument("first", first), Argument("last", last))
      )

  }

  type TreeEntry

  object TreeEntry {

    /**
      * Flat path of the entry
      */
    def flatPath: SelectionBuilder[TreeEntry, String] = Field("flatPath", Scalar())

    /**
      * ID of the entry
      */
    def id: SelectionBuilder[TreeEntry, String] = Field("id", Scalar())

    /**
      * Name of the entry
      */
    def name: SelectionBuilder[TreeEntry, String] = Field("name", Scalar())

    /**
      * Path of the entry
      */
    def path: SelectionBuilder[TreeEntry, String] = Field("path", Scalar())

    /**
      * Last commit sha for the entry
      */
    def sha: SelectionBuilder[TreeEntry, String] = Field("sha", Scalar())

    /**
      * Type of tree entry
      */
    def `type`: SelectionBuilder[TreeEntry, EntryType] = Field("type", Scalar())

    /**
      * Web path for the tree entry (directory)
      */
    def webPath: SelectionBuilder[TreeEntry, Option[String]] = Field("webPath", OptionOf(Scalar()))

    /**
      * Web URL for the tree entry (directory)
      */
    def webUrl: SelectionBuilder[TreeEntry, Option[String]] = Field("webUrl", OptionOf(Scalar()))
  }

  type TreeEntryConnection

  object TreeEntryConnection {

    /**
      * A list of edges.
      */
    def edges[A](innerSelection: SelectionBuilder[TreeEntryEdge, A]): SelectionBuilder[TreeEntryConnection, Option[List[Option[A]]]] =
      Field("edges", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * A list of nodes.
      */
    def nodes[A](innerSelection: SelectionBuilder[TreeEntry, A]): SelectionBuilder[TreeEntryConnection, Option[List[Option[A]]]] =
      Field("nodes", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * Information to aid in pagination.
      */
    def pageInfo[A](innerSelection: SelectionBuilder[PageInfo, A]): SelectionBuilder[TreeEntryConnection, A] =
      Field("pageInfo", Obj(innerSelection))
  }

  type TreeEntryEdge

  object TreeEntryEdge {

    /**
      * A cursor for use in pagination.
      */
    def cursor: SelectionBuilder[TreeEntryEdge, String] = Field("cursor", Scalar())

    /**
      * The item at the end of the edge.
      */
    def node[A](innerSelection: SelectionBuilder[TreeEntry, A]): SelectionBuilder[TreeEntryEdge, Option[A]] =
      Field("node", OptionOf(Obj(innerSelection)))
  }

  type UpdateAlertStatusPayload

  object UpdateAlertStatusPayload {

    /**
      * The alert after mutation
      */
    def alert[A](innerSelection: SelectionBuilder[AlertManagementAlert, A]): SelectionBuilder[UpdateAlertStatusPayload, Option[A]] =
      Field("alert", OptionOf(Obj(innerSelection)))

    /**
      * A unique identifier for the client performing the mutation.
      */
    def clientMutationId: SelectionBuilder[UpdateAlertStatusPayload, Option[String]] = Field("clientMutationId", OptionOf(Scalar()))

    /**
      * Errors encountered during execution of the mutation.
      */
    def errors: SelectionBuilder[UpdateAlertStatusPayload, List[String]] = Field("errors", ListOf(Scalar()))

    /**
      * The issue created after mutation
      */
    def issue[A](innerSelection: SelectionBuilder[Issue, A]): SelectionBuilder[UpdateAlertStatusPayload, Option[A]] =
      Field("issue", OptionOf(Obj(innerSelection)))

    /**
      * The todo after mutation
      */
    def todo[A](innerSelection: SelectionBuilder[Todo, A]): SelectionBuilder[UpdateAlertStatusPayload, Option[A]] =
      Field("todo", OptionOf(Obj(innerSelection)))
  }

  type UpdateContainerExpirationPolicyPayload

  object UpdateContainerExpirationPolicyPayload {

    /**
      * A unique identifier for the client performing the mutation.
      */
    def clientMutationId: SelectionBuilder[UpdateContainerExpirationPolicyPayload, Option[String]] =
      Field("clientMutationId", OptionOf(Scalar()))

    /**
      * The container expiration policy after mutation
      */
    def containerExpirationPolicy[A](
      innerSelection: SelectionBuilder[ContainerExpirationPolicy, A]
    ): SelectionBuilder[UpdateContainerExpirationPolicyPayload, Option[A]] =
      Field("containerExpirationPolicy", OptionOf(Obj(innerSelection)))

    /**
      * Errors encountered during execution of the mutation.
      */
    def errors: SelectionBuilder[UpdateContainerExpirationPolicyPayload, List[String]] = Field("errors", ListOf(Scalar()))
  }

  type UpdateEpicPayload

  object UpdateEpicPayload {

    /**
      * A unique identifier for the client performing the mutation.
      */
    def clientMutationId: SelectionBuilder[UpdateEpicPayload, Option[String]] = Field("clientMutationId", OptionOf(Scalar()))

    /**
      * The epic after mutation
      */
    def epic[A](innerSelection: SelectionBuilder[Epic, A]): SelectionBuilder[UpdateEpicPayload, Option[A]] =
      Field("epic", OptionOf(Obj(innerSelection)))

    /**
      * Errors encountered during execution of the mutation.
      */
    def errors: SelectionBuilder[UpdateEpicPayload, List[String]] = Field("errors", ListOf(Scalar()))
  }

  type UpdateImageDiffNotePayload

  object UpdateImageDiffNotePayload {

    /**
      * A unique identifier for the client performing the mutation.
      */
    def clientMutationId: SelectionBuilder[UpdateImageDiffNotePayload, Option[String]] = Field("clientMutationId", OptionOf(Scalar()))

    /**
      * Errors encountered during execution of the mutation.
      */
    def errors: SelectionBuilder[UpdateImageDiffNotePayload, List[String]] = Field("errors", ListOf(Scalar()))

    /**
      * The note after mutation
      */
    def note[A](innerSelection: SelectionBuilder[Note, A]): SelectionBuilder[UpdateImageDiffNotePayload, Option[A]] =
      Field("note", OptionOf(Obj(innerSelection)))
  }

  type UpdateIssuePayload

  object UpdateIssuePayload {

    /**
      * A unique identifier for the client performing the mutation.
      */
    def clientMutationId: SelectionBuilder[UpdateIssuePayload, Option[String]] = Field("clientMutationId", OptionOf(Scalar()))

    /**
      * Errors encountered during execution of the mutation.
      */
    def errors: SelectionBuilder[UpdateIssuePayload, List[String]] = Field("errors", ListOf(Scalar()))

    /**
      * The issue after mutation
      */
    def issue[A](innerSelection: SelectionBuilder[Issue, A]): SelectionBuilder[UpdateIssuePayload, Option[A]] =
      Field("issue", OptionOf(Obj(innerSelection)))
  }

  type UpdateIterationPayload

  object UpdateIterationPayload {

    /**
      * A unique identifier for the client performing the mutation.
      */
    def clientMutationId: SelectionBuilder[UpdateIterationPayload, Option[String]] = Field("clientMutationId", OptionOf(Scalar()))

    /**
      * Errors encountered during execution of the mutation.
      */
    def errors: SelectionBuilder[UpdateIterationPayload, List[String]] = Field("errors", ListOf(Scalar()))

    /**
      * The updated iteration
      */
    def iteration[A](innerSelection: SelectionBuilder[Iteration, A]): SelectionBuilder[UpdateIterationPayload, Option[A]] =
      Field("iteration", OptionOf(Obj(innerSelection)))
  }

  type UpdateNotePayload

  object UpdateNotePayload {

    /**
      * A unique identifier for the client performing the mutation.
      */
    def clientMutationId: SelectionBuilder[UpdateNotePayload, Option[String]] = Field("clientMutationId", OptionOf(Scalar()))

    /**
      * Errors encountered during execution of the mutation.
      */
    def errors: SelectionBuilder[UpdateNotePayload, List[String]] = Field("errors", ListOf(Scalar()))

    /**
      * The note after mutation
      */
    def note[A](innerSelection: SelectionBuilder[Note, A]): SelectionBuilder[UpdateNotePayload, Option[A]] =
      Field("note", OptionOf(Obj(innerSelection)))
  }

  type UpdateRequirementPayload

  object UpdateRequirementPayload {

    /**
      * A unique identifier for the client performing the mutation.
      */
    def clientMutationId: SelectionBuilder[UpdateRequirementPayload, Option[String]] = Field("clientMutationId", OptionOf(Scalar()))

    /**
      * Errors encountered during execution of the mutation.
      */
    def errors: SelectionBuilder[UpdateRequirementPayload, List[String]] = Field("errors", ListOf(Scalar()))

    /**
      * The requirement after mutation
      */
    def requirement[A](innerSelection: SelectionBuilder[Requirement, A]): SelectionBuilder[UpdateRequirementPayload, Option[A]] =
      Field("requirement", OptionOf(Obj(innerSelection)))
  }

  type UpdateSnippetPayload

  object UpdateSnippetPayload {

    /**
      * A unique identifier for the client performing the mutation.
      */
    def clientMutationId: SelectionBuilder[UpdateSnippetPayload, Option[String]] = Field("clientMutationId", OptionOf(Scalar()))

    /**
      * Errors encountered during execution of the mutation.
      */
    def errors: SelectionBuilder[UpdateSnippetPayload, List[String]] = Field("errors", ListOf(Scalar()))

    /**
      * The snippet after mutation
      */
    def snippet[A](innerSelection: SelectionBuilder[Snippet, A]): SelectionBuilder[UpdateSnippetPayload, Option[A]] =
      Field("snippet", OptionOf(Obj(innerSelection)))
  }

  type User

  object User {

    /**
      * Merge Requests assigned to the user
      */
    def assignedMergeRequests[A](
      iids: Option[List[String]] = None,
      sourceBranches: Option[List[String]] = None,
      targetBranches: Option[List[String]] = None,
      state: Option[MergeRequestState] = None,
      labels: Option[List[String]] = None,
      projectPath: Option[String] = None,
      projectId: Option[String] = None,
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[MergeRequestConnection, A]
    ): SelectionBuilder[User, Option[A]] =
      Field(
        "assignedMergeRequests",
        OptionOf(Obj(innerSelection)),
        arguments = List(
          Argument("iids", iids),
          Argument("sourceBranches", sourceBranches),
          Argument("targetBranches", targetBranches),
          Argument("state", state),
          Argument("labels", labels),
          Argument("projectPath", projectPath),
          Argument("projectId", projectId),
          Argument("after", after),
          Argument("before", before),
          Argument("first", first),
          Argument("last", last)
        )
      )

    /**
      * Merge Requests authored by the user
      */
    def authoredMergeRequests[A](
      iids: Option[List[String]] = None,
      sourceBranches: Option[List[String]] = None,
      targetBranches: Option[List[String]] = None,
      state: Option[MergeRequestState] = None,
      labels: Option[List[String]] = None,
      projectPath: Option[String] = None,
      projectId: Option[String] = None,
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[MergeRequestConnection, A]
    ): SelectionBuilder[User, Option[A]] =
      Field(
        "authoredMergeRequests",
        OptionOf(Obj(innerSelection)),
        arguments = List(
          Argument("iids", iids),
          Argument("sourceBranches", sourceBranches),
          Argument("targetBranches", targetBranches),
          Argument("state", state),
          Argument("labels", labels),
          Argument("projectPath", projectPath),
          Argument("projectId", projectId),
          Argument("after", after),
          Argument("before", before),
          Argument("first", first),
          Argument("last", last)
        )
      )

    /**
      * URL of the user's avatar
      */
    def avatarUrl: SelectionBuilder[User, Option[String]] = Field("avatarUrl", OptionOf(Scalar()))

    /**
      * Group memberships of the user
      */
    def groupMemberships[A](
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[GroupMemberConnection, A]
    ): SelectionBuilder[User, Option[A]] =
      Field(
        "groupMemberships",
        OptionOf(Obj(innerSelection)),
        arguments = List(Argument("after", after), Argument("before", before), Argument("first", first), Argument("last", last))
      )

    /**
      * ID of the user
      */
    def id: SelectionBuilder[User, String] = Field("id", Scalar())

    /**
      * Human-readable name of the user
      */
    def name: SelectionBuilder[User, String] = Field("name", Scalar())

    /**
      * Project memberships of the user
      */
    def projectMemberships[A](
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[ProjectMemberConnection, A]
    ): SelectionBuilder[User, Option[A]] =
      Field(
        "projectMemberships",
        OptionOf(Obj(innerSelection)),
        arguments = List(Argument("after", after), Argument("before", before), Argument("first", first), Argument("last", last))
      )

    /**
      * Snippets authored by the user
      */
    def snippets[A](
      ids: Option[List[String]] = None,
      visibility: Option[VisibilityScopesEnum] = None,
      `type`: Option[TypeEnum] = None,
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[SnippetConnection, A]
    ): SelectionBuilder[User, Option[A]] =
      Field(
        "snippets",
        OptionOf(Obj(innerSelection)),
        arguments = List(
          Argument("ids", ids),
          Argument("visibility", visibility),
          Argument("type", `type`),
          Argument("after", after),
          Argument("before", before),
          Argument("first", first),
          Argument("last", last)
        )
      )

    /**
      * State of the user
      */
    def state: SelectionBuilder[User, UserState] = Field("state", Scalar())

    /**
      * Todos of the user
      */
    def todos[A](
      action: Option[List[TodoActionEnum]] = None,
      authorId: Option[List[String]] = None,
      projectId: Option[List[String]] = None,
      groupId: Option[List[String]] = None,
      state: Option[List[TodoStateEnum]] = None,
      `type`: Option[List[TodoTargetEnum]] = None,
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[TodoConnection, A]
    ): SelectionBuilder[User, A] =
      Field(
        "todos",
        Obj(innerSelection),
        arguments = List(
          Argument("action", action),
          Argument("authorId", authorId),
          Argument("projectId", projectId),
          Argument("groupId", groupId),
          Argument("state", state),
          Argument("type", `type`),
          Argument("after", after),
          Argument("before", before),
          Argument("first", first),
          Argument("last", last)
        )
      )

    /**
      * Permissions for the current user on the resource
      */
    def userPermissions[A](innerSelection: SelectionBuilder[UserPermissions, A]): SelectionBuilder[User, A] =
      Field("userPermissions", Obj(innerSelection))

    /**
      * Username of the user. Unique within this instance of GitLab
      */
    def username: SelectionBuilder[User, String] = Field("username", Scalar())

    /**
      * Web path of the user
      */
    def webPath: SelectionBuilder[User, String] = Field("webPath", Scalar())

    /**
      * Web URL of the user
      */
    def webUrl: SelectionBuilder[User, String] = Field("webUrl", Scalar())
  }

  type UserConnection

  object UserConnection {

    /**
      * A list of edges.
      */
    def edges[A](innerSelection: SelectionBuilder[UserEdge, A]): SelectionBuilder[UserConnection, Option[List[Option[A]]]] =
      Field("edges", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * A list of nodes.
      */
    def nodes[A](innerSelection: SelectionBuilder[User, A]): SelectionBuilder[UserConnection, Option[List[Option[A]]]] =
      Field("nodes", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * Information to aid in pagination.
      */
    def pageInfo[A](innerSelection: SelectionBuilder[PageInfo, A]): SelectionBuilder[UserConnection, A] =
      Field("pageInfo", Obj(innerSelection))
  }

  type UserEdge

  object UserEdge {

    /**
      * A cursor for use in pagination.
      */
    def cursor: SelectionBuilder[UserEdge, String] = Field("cursor", Scalar())

    /**
      * The item at the end of the edge.
      */
    def node[A](innerSelection: SelectionBuilder[User, A]): SelectionBuilder[UserEdge, Option[A]] =
      Field("node", OptionOf(Obj(innerSelection)))
  }

  type UserPermissions

  object UserPermissions {

    /**
      * Indicates the user can perform `create_snippet` on this resource
      */
    def createSnippet: SelectionBuilder[UserPermissions, Boolean] = Field("createSnippet", Scalar())
  }

  type VulnerabilitiesCountByDayAndSeverity

  object VulnerabilitiesCountByDayAndSeverity {

    /**
      * Number of vulnerabilities
      */
    def count: SelectionBuilder[VulnerabilitiesCountByDayAndSeverity, Option[Int]] = Field("count", OptionOf(Scalar()))

    /**
      * Date for the count
      */
    def day: SelectionBuilder[VulnerabilitiesCountByDayAndSeverity, Option[ISO8601Date]] = Field("day", OptionOf(Scalar()))

    /**
      * Severity of the counted vulnerabilities
      */
    def severity: SelectionBuilder[VulnerabilitiesCountByDayAndSeverity, Option[VulnerabilitySeverity]] =
      Field("severity", OptionOf(Scalar()))
  }

  type VulnerabilitiesCountByDayAndSeverityConnection

  object VulnerabilitiesCountByDayAndSeverityConnection {

    /**
      * A list of edges.
      */
    def edges[A](
      innerSelection: SelectionBuilder[VulnerabilitiesCountByDayAndSeverityEdge, A]
    ): SelectionBuilder[VulnerabilitiesCountByDayAndSeverityConnection, Option[List[Option[A]]]] =
      Field("edges", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * A list of nodes.
      */
    def nodes[A](
      innerSelection: SelectionBuilder[VulnerabilitiesCountByDayAndSeverity, A]
    ): SelectionBuilder[VulnerabilitiesCountByDayAndSeverityConnection, Option[List[Option[A]]]] =
      Field("nodes", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * Information to aid in pagination.
      */
    def pageInfo[A](innerSelection: SelectionBuilder[PageInfo, A]): SelectionBuilder[VulnerabilitiesCountByDayAndSeverityConnection, A] =
      Field("pageInfo", Obj(innerSelection))
  }

  type VulnerabilitiesCountByDayAndSeverityEdge

  object VulnerabilitiesCountByDayAndSeverityEdge {

    /**
      * A cursor for use in pagination.
      */
    def cursor: SelectionBuilder[VulnerabilitiesCountByDayAndSeverityEdge, String] = Field("cursor", Scalar())

    /**
      * The item at the end of the edge.
      */
    def node[A](
      innerSelection: SelectionBuilder[VulnerabilitiesCountByDayAndSeverity, A]
    ): SelectionBuilder[VulnerabilitiesCountByDayAndSeverityEdge, Option[A]] = Field("node", OptionOf(Obj(innerSelection)))

  }

  type Vulnerability

  object Vulnerability {

    /**
      * Description of the vulnerability
      */
    def description: SelectionBuilder[Vulnerability, Option[String]] = Field("description", OptionOf(Scalar()))

    /**
      * GraphQL ID of the vulnerability
      */
    def id: SelectionBuilder[Vulnerability, String] = Field("id", Scalar())

    /**
      * Identifiers of the vulnerability.
      */
    def identifiers[A](innerSelection: SelectionBuilder[VulnerabilityIdentifier, A]): SelectionBuilder[Vulnerability, List[A]] =
      Field("identifiers", ListOf(Obj(innerSelection)))

    /**
      * List of issue links related to the vulnerability
      */
    def issueLinks[A](
      linkType: Option[VulnerabilityIssueLinkType] = None,
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[VulnerabilityIssueLinkConnection, A]
    ): SelectionBuilder[Vulnerability, A] =
      Field(
        "issueLinks",
        Obj(innerSelection),
        arguments = List(
          Argument("linkType", linkType),
          Argument("after", after),
          Argument("before", before),
          Argument("first", first),
          Argument("last", last)
        )
      )

    /**
      * Location metadata for the vulnerability. Its fields depend on the type of security scan that found the vulnerability
      */
    def location[A](
      onVulnerabilityLocationContainerScanning: SelectionBuilder[VulnerabilityLocationContainerScanning, A],
      onVulnerabilityLocationCoverageFuzzing: SelectionBuilder[VulnerabilityLocationCoverageFuzzing, A],
      onVulnerabilityLocationDast: SelectionBuilder[VulnerabilityLocationDast, A],
      onVulnerabilityLocationDependencyScanning: SelectionBuilder[VulnerabilityLocationDependencyScanning, A],
      onVulnerabilityLocationSast: SelectionBuilder[VulnerabilityLocationSast, A],
      onVulnerabilityLocationSecretDetection: SelectionBuilder[VulnerabilityLocationSecretDetection, A]
    ): SelectionBuilder[Vulnerability, Option[A]] =
      Field(
        "location",
        OptionOf(
          ChoiceOf(
            Map(
              "VulnerabilityLocationContainerScanning" -> Obj(onVulnerabilityLocationContainerScanning),
              "VulnerabilityLocationCoverageFuzzing" -> Obj(onVulnerabilityLocationCoverageFuzzing),
              "VulnerabilityLocationDast" -> Obj(onVulnerabilityLocationDast),
              "VulnerabilityLocationDependencyScanning" -> Obj(onVulnerabilityLocationDependencyScanning),
              "VulnerabilityLocationSast" -> Obj(onVulnerabilityLocationSast),
              "VulnerabilityLocationSecretDetection" -> Obj(onVulnerabilityLocationSecretDetection)
            )
          )
        )
      )

    /**
      * Primary identifier of the vulnerability.
      */
    def primaryIdentifier[A](innerSelection: SelectionBuilder[VulnerabilityIdentifier, A]): SelectionBuilder[Vulnerability, Option[A]] =
      Field("primaryIdentifier", OptionOf(Obj(innerSelection)))

    /**
      * The project on which the vulnerability was found
      */
    def project[A](innerSelection: SelectionBuilder[Project, A]): SelectionBuilder[Vulnerability, Option[A]] =
      Field("project", OptionOf(Obj(innerSelection)))

    /**
      * Type of the security report that found the vulnerability (SAST, DEPENDENCY_SCANNING, CONTAINER_SCANNING, DAST, SECRET_DETECTION, COVERAGE_FUZZING)
      */
    def reportType: SelectionBuilder[Vulnerability, Option[VulnerabilityReportType]] = Field("reportType", OptionOf(Scalar()))

    /**
      * Scanner metadata for the vulnerability.
      */
    def scanner[A](innerSelection: SelectionBuilder[VulnerabilityScanner, A]): SelectionBuilder[Vulnerability, Option[A]] =
      Field("scanner", OptionOf(Obj(innerSelection)))

    /**
      * Severity of the vulnerability (INFO, UNKNOWN, LOW, MEDIUM, HIGH, CRITICAL)
      */
    def severity: SelectionBuilder[Vulnerability, Option[VulnerabilitySeverity]] = Field("severity", OptionOf(Scalar()))

    /**
      * State of the vulnerability (DETECTED, DISMISSED, RESOLVED, CONFIRMED)
      */
    def state: SelectionBuilder[Vulnerability, Option[VulnerabilityState]] = Field("state", OptionOf(Scalar()))

    /**
      * Title of the vulnerability
      */
    def title: SelectionBuilder[Vulnerability, Option[String]] = Field("title", OptionOf(Scalar()))

    /**
      * Number of user notes attached to the vulnerability
      */
    def userNotesCount: SelectionBuilder[Vulnerability, Int] = Field("userNotesCount", Scalar())

    /**
      * Permissions for the current user on the resource
      */
    def userPermissions[A](innerSelection: SelectionBuilder[VulnerabilityPermissions, A]): SelectionBuilder[Vulnerability, A] =
      Field("userPermissions", Obj(innerSelection))

    /**
      * URL to the vulnerability's details page
      */
    def vulnerabilityPath: SelectionBuilder[Vulnerability, Option[String]] = Field("vulnerabilityPath", OptionOf(Scalar()))
  }

  type VulnerabilityConnection

  object VulnerabilityConnection {

    /**
      * A list of edges.
      */
    def edges[A](
      innerSelection: SelectionBuilder[VulnerabilityEdge, A]
    ): SelectionBuilder[VulnerabilityConnection, Option[List[Option[A]]]] = Field("edges", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * A list of nodes.
      */
    def nodes[A](innerSelection: SelectionBuilder[Vulnerability, A]): SelectionBuilder[VulnerabilityConnection, Option[List[Option[A]]]] =
      Field("nodes", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * Information to aid in pagination.
      */
    def pageInfo[A](innerSelection: SelectionBuilder[PageInfo, A]): SelectionBuilder[VulnerabilityConnection, A] =
      Field("pageInfo", Obj(innerSelection))
  }

  type VulnerabilityEdge

  object VulnerabilityEdge {

    /**
      * A cursor for use in pagination.
      */
    def cursor: SelectionBuilder[VulnerabilityEdge, String] = Field("cursor", Scalar())

    /**
      * The item at the end of the edge.
      */
    def node[A](innerSelection: SelectionBuilder[Vulnerability, A]): SelectionBuilder[VulnerabilityEdge, Option[A]] =
      Field("node", OptionOf(Obj(innerSelection)))
  }

  type VulnerabilityIdentifier

  object VulnerabilityIdentifier {

    /**
      * External ID of the vulnerability identifier
      */
    def externalId: SelectionBuilder[VulnerabilityIdentifier, Option[String]] = Field("externalId", OptionOf(Scalar()))

    /**
      * External type of the vulnerability identifier
      */
    def externalType: SelectionBuilder[VulnerabilityIdentifier, Option[String]] = Field("externalType", OptionOf(Scalar()))

    /**
      * Name of the vulnerability identifier
      */
    def name: SelectionBuilder[VulnerabilityIdentifier, Option[String]] = Field("name", OptionOf(Scalar()))

    /**
      * URL of the vulnerability identifier
      */
    def url: SelectionBuilder[VulnerabilityIdentifier, Option[String]] = Field("url", OptionOf(Scalar()))
  }

  type VulnerabilityIssueLink

  object VulnerabilityIssueLink {

    /**
      * GraphQL ID of the vulnerability
      */
    def id: SelectionBuilder[VulnerabilityIssueLink, String] = Field("id", Scalar())

    /**
      * The issue attached to issue link
      */
    def issue[A](innerSelection: SelectionBuilder[Issue, A]): SelectionBuilder[VulnerabilityIssueLink, A] =
      Field("issue", Obj(innerSelection))

    /**
      * Type of the issue link
      */
    def linkType: SelectionBuilder[VulnerabilityIssueLink, VulnerabilityIssueLinkType] = Field("linkType", Scalar())
  }

  type VulnerabilityIssueLinkConnection

  object VulnerabilityIssueLinkConnection {

    /**
      * A list of edges.
      */
    def edges[A](
      innerSelection: SelectionBuilder[VulnerabilityIssueLinkEdge, A]
    ): SelectionBuilder[VulnerabilityIssueLinkConnection, Option[List[Option[A]]]] =
      Field("edges", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * A list of nodes.
      */
    def nodes[A](
      innerSelection: SelectionBuilder[VulnerabilityIssueLink, A]
    ): SelectionBuilder[VulnerabilityIssueLinkConnection, Option[List[Option[A]]]] =
      Field("nodes", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * Information to aid in pagination.
      */
    def pageInfo[A](innerSelection: SelectionBuilder[PageInfo, A]): SelectionBuilder[VulnerabilityIssueLinkConnection, A] =
      Field("pageInfo", Obj(innerSelection))
  }

  type VulnerabilityIssueLinkEdge

  object VulnerabilityIssueLinkEdge {

    /**
      * A cursor for use in pagination.
      */
    def cursor: SelectionBuilder[VulnerabilityIssueLinkEdge, String] = Field("cursor", Scalar())

    /**
      * The item at the end of the edge.
      */
    def node[A](innerSelection: SelectionBuilder[VulnerabilityIssueLink, A]): SelectionBuilder[VulnerabilityIssueLinkEdge, Option[A]] =
      Field("node", OptionOf(Obj(innerSelection)))
  }

  type VulnerabilityLocationContainerScanning

  object VulnerabilityLocationContainerScanning {

    /**
      * Dependency containing the vulnerability
      */
    def dependency[A](
      innerSelection: SelectionBuilder[VulnerableDependency, A]
    ): SelectionBuilder[VulnerabilityLocationContainerScanning, Option[A]] = Field("dependency", OptionOf(Obj(innerSelection)))

    /**
      * Name of the vulnerable container image
      */
    def image: SelectionBuilder[VulnerabilityLocationContainerScanning, Option[String]] = Field("image", OptionOf(Scalar()))

    /**
      * Operating system that runs on the vulnerable container image
      */
    def operatingSystem: SelectionBuilder[VulnerabilityLocationContainerScanning, Option[String]] =
      Field("operatingSystem", OptionOf(Scalar()))
  }

  type VulnerabilityLocationCoverageFuzzing

  object VulnerabilityLocationCoverageFuzzing {

    /**
      * Number of the last relevant line in the vulnerable file
      */
    def endLine: SelectionBuilder[VulnerabilityLocationCoverageFuzzing, Option[String]] = Field("endLine", OptionOf(Scalar()))

    /**
      * Path to the vulnerable file
      */
    def file: SelectionBuilder[VulnerabilityLocationCoverageFuzzing, Option[String]] = Field("file", OptionOf(Scalar()))

    /**
      * Number of the first relevant line in the vulnerable file
      */
    def startLine: SelectionBuilder[VulnerabilityLocationCoverageFuzzing, Option[String]] = Field("startLine", OptionOf(Scalar()))

    /**
      * Class containing the vulnerability
      */
    def vulnerableClass: SelectionBuilder[VulnerabilityLocationCoverageFuzzing, Option[String]] =
      Field("vulnerableClass", OptionOf(Scalar()))

    /**
      * Method containing the vulnerability
      */
    def vulnerableMethod: SelectionBuilder[VulnerabilityLocationCoverageFuzzing, Option[String]] =
      Field("vulnerableMethod", OptionOf(Scalar()))
  }

  type VulnerabilityLocationDast

  object VulnerabilityLocationDast {

    /**
      * Domain name of the vulnerable request
      */
    def hostname: SelectionBuilder[VulnerabilityLocationDast, Option[String]] = Field("hostname", OptionOf(Scalar()))

    /**
      * Query parameter for the URL on which the vulnerability occurred
      */
    def param: SelectionBuilder[VulnerabilityLocationDast, Option[String]] = Field("param", OptionOf(Scalar()))

    /**
      * URL path and query string of the vulnerable request
      */
    def path: SelectionBuilder[VulnerabilityLocationDast, Option[String]] = Field("path", OptionOf(Scalar()))

    /**
      * HTTP method of the vulnerable request
      */
    def requestMethod: SelectionBuilder[VulnerabilityLocationDast, Option[String]] = Field("requestMethod", OptionOf(Scalar()))
  }

  type VulnerabilityLocationDependencyScanning

  object VulnerabilityLocationDependencyScanning {

    /**
      * Dependency containing the vulnerability
      */
    def dependency[A](
      innerSelection: SelectionBuilder[VulnerableDependency, A]
    ): SelectionBuilder[VulnerabilityLocationDependencyScanning, Option[A]] = Field("dependency", OptionOf(Obj(innerSelection)))

    /**
      * Path to the vulnerable file
      */
    def file: SelectionBuilder[VulnerabilityLocationDependencyScanning, Option[String]] = Field("file", OptionOf(Scalar()))
  }

  type VulnerabilityLocationSast

  object VulnerabilityLocationSast {

    /**
      * Number of the last relevant line in the vulnerable file
      */
    def endLine: SelectionBuilder[VulnerabilityLocationSast, Option[String]] = Field("endLine", OptionOf(Scalar()))

    /**
      * Path to the vulnerable file
      */
    def file: SelectionBuilder[VulnerabilityLocationSast, Option[String]] = Field("file", OptionOf(Scalar()))

    /**
      * Number of the first relevant line in the vulnerable file
      */
    def startLine: SelectionBuilder[VulnerabilityLocationSast, Option[String]] = Field("startLine", OptionOf(Scalar()))

    /**
      * Class containing the vulnerability
      */
    def vulnerableClass: SelectionBuilder[VulnerabilityLocationSast, Option[String]] = Field("vulnerableClass", OptionOf(Scalar()))

    /**
      * Method containing the vulnerability
      */
    def vulnerableMethod: SelectionBuilder[VulnerabilityLocationSast, Option[String]] = Field("vulnerableMethod", OptionOf(Scalar()))
  }

  type VulnerabilityLocationSecretDetection

  object VulnerabilityLocationSecretDetection {

    /**
      * Number of the last relevant line in the vulnerable file
      */
    def endLine: SelectionBuilder[VulnerabilityLocationSecretDetection, Option[String]] = Field("endLine", OptionOf(Scalar()))

    /**
      * Path to the vulnerable file
      */
    def file: SelectionBuilder[VulnerabilityLocationSecretDetection, Option[String]] = Field("file", OptionOf(Scalar()))

    /**
      * Number of the first relevant line in the vulnerable file
      */
    def startLine: SelectionBuilder[VulnerabilityLocationSecretDetection, Option[String]] = Field("startLine", OptionOf(Scalar()))

    /**
      * Class containing the vulnerability
      */
    def vulnerableClass: SelectionBuilder[VulnerabilityLocationSecretDetection, Option[String]] =
      Field("vulnerableClass", OptionOf(Scalar()))

    /**
      * Method containing the vulnerability
      */
    def vulnerableMethod: SelectionBuilder[VulnerabilityLocationSecretDetection, Option[String]] =
      Field("vulnerableMethod", OptionOf(Scalar()))
  }

  type VulnerabilityPermissions

  object VulnerabilityPermissions {

    /**
      * Indicates the user can perform `admin_vulnerability` on this resource
      */
    def adminVulnerability: SelectionBuilder[VulnerabilityPermissions, Boolean] = Field("adminVulnerability", Scalar())

    /**
      * Indicates the user can perform `admin_vulnerability_issue_link` on this resource
      */
    def adminVulnerabilityIssueLink: SelectionBuilder[VulnerabilityPermissions, Boolean] = Field("adminVulnerabilityIssueLink", Scalar())

    /**
      * Indicates the user can perform `create_vulnerability` on this resource
      */
    def createVulnerability: SelectionBuilder[VulnerabilityPermissions, Boolean] = Field("createVulnerability", Scalar())

    /**
      * Indicates the user can perform `create_vulnerability_export` on this resource
      */
    def createVulnerabilityExport: SelectionBuilder[VulnerabilityPermissions, Boolean] = Field("createVulnerabilityExport", Scalar())

    /**
      * Indicates the user can perform `create_vulnerability_feedback` on this resource
      */
    def createVulnerabilityFeedback: SelectionBuilder[VulnerabilityPermissions, Boolean] = Field("createVulnerabilityFeedback", Scalar())

    /**
      * Indicates the user can perform `destroy_vulnerability_feedback` on this resource
      */
    def destroyVulnerabilityFeedback: SelectionBuilder[VulnerabilityPermissions, Boolean] = Field("destroyVulnerabilityFeedback", Scalar())

    /**
      * Indicates the user can perform `read_vulnerability_feedback` on this resource
      */
    def readVulnerabilityFeedback: SelectionBuilder[VulnerabilityPermissions, Boolean] = Field("readVulnerabilityFeedback", Scalar())

    /**
      * Indicates the user can perform `update_vulnerability_feedback` on this resource
      */
    def updateVulnerabilityFeedback: SelectionBuilder[VulnerabilityPermissions, Boolean] = Field("updateVulnerabilityFeedback", Scalar())
  }

  type VulnerabilityScanner

  object VulnerabilityScanner {

    /**
      * External ID of the vulnerability scanner
      */
    def externalId: SelectionBuilder[VulnerabilityScanner, Option[String]] = Field("externalId", OptionOf(Scalar()))

    /**
      * Name of the vulnerability scanner
      */
    def name: SelectionBuilder[VulnerabilityScanner, Option[String]] = Field("name", OptionOf(Scalar()))

    /**
      * Type of the vulnerability report
      */
    def reportType: SelectionBuilder[VulnerabilityScanner, Option[VulnerabilityReportType]] = Field("reportType", OptionOf(Scalar()))

    /**
      * Vendor of the vulnerability scanner
      */
    def vendor: SelectionBuilder[VulnerabilityScanner, Option[String]] = Field("vendor", OptionOf(Scalar()))
  }

  type VulnerabilityScannerConnection

  object VulnerabilityScannerConnection {

    /**
      * A list of edges.
      */
    def edges[A](
      innerSelection: SelectionBuilder[VulnerabilityScannerEdge, A]
    ): SelectionBuilder[VulnerabilityScannerConnection, Option[List[Option[A]]]] =
      Field("edges", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * A list of nodes.
      */
    def nodes[A](
      innerSelection: SelectionBuilder[VulnerabilityScanner, A]
    ): SelectionBuilder[VulnerabilityScannerConnection, Option[List[Option[A]]]] =
      Field("nodes", OptionOf(ListOf(OptionOf(Obj(innerSelection)))))

    /**
      * Information to aid in pagination.
      */
    def pageInfo[A](innerSelection: SelectionBuilder[PageInfo, A]): SelectionBuilder[VulnerabilityScannerConnection, A] =
      Field("pageInfo", Obj(innerSelection))
  }

  type VulnerabilityScannerEdge

  object VulnerabilityScannerEdge {

    /**
      * A cursor for use in pagination.
      */
    def cursor: SelectionBuilder[VulnerabilityScannerEdge, String] = Field("cursor", Scalar())

    /**
      * The item at the end of the edge.
      */
    def node[A](innerSelection: SelectionBuilder[VulnerabilityScanner, A]): SelectionBuilder[VulnerabilityScannerEdge, Option[A]] =
      Field("node", OptionOf(Obj(innerSelection)))
  }

  type VulnerabilitySeveritiesCount

  object VulnerabilitySeveritiesCount {

    /**
      * Number of vulnerabilities of CRITICAL severity of the project
      */
    def critical: SelectionBuilder[VulnerabilitySeveritiesCount, Option[Int]] = Field("critical", OptionOf(Scalar()))

    /**
      * Number of vulnerabilities of HIGH severity of the project
      */
    def high: SelectionBuilder[VulnerabilitySeveritiesCount, Option[Int]] = Field("high", OptionOf(Scalar()))

    /**
      * Number of vulnerabilities of INFO severity of the project
      */
    def info: SelectionBuilder[VulnerabilitySeveritiesCount, Option[Int]] = Field("info", OptionOf(Scalar()))

    /**
      * Number of vulnerabilities of LOW severity of the project
      */
    def low: SelectionBuilder[VulnerabilitySeveritiesCount, Option[Int]] = Field("low", OptionOf(Scalar()))

    /**
      * Number of vulnerabilities of MEDIUM severity of the project
      */
    def medium: SelectionBuilder[VulnerabilitySeveritiesCount, Option[Int]] = Field("medium", OptionOf(Scalar()))

    /**
      * Number of vulnerabilities of UNKNOWN severity of the project
      */
    def unknown: SelectionBuilder[VulnerabilitySeveritiesCount, Option[Int]] = Field("unknown", OptionOf(Scalar()))
  }

  type VulnerableDependency

  object VulnerableDependency {

    /**
      * The package associated with the vulnerable dependency
      */
    def `package`[A](innerSelection: SelectionBuilder[VulnerablePackage, A]): SelectionBuilder[VulnerableDependency, Option[A]] =
      Field("package", OptionOf(Obj(innerSelection)))

    /**
      * The version of the vulnerable dependency
      */
    def version: SelectionBuilder[VulnerableDependency, Option[String]] = Field("version", OptionOf(Scalar()))
  }

  type VulnerablePackage

  object VulnerablePackage {

    /**
      * The name of the vulnerable package
      */
    def name: SelectionBuilder[VulnerablePackage, Option[String]] = Field("name", OptionOf(Scalar()))
  }

  case class AddAwardEmojiInput(awardableId: String, name: String, clientMutationId: Option[String] = None)

  object AddAwardEmojiInput {

    implicit val encoder: ArgEncoder[AddAwardEmojiInput] = new ArgEncoder[AddAwardEmojiInput] {

      override def encode(value: AddAwardEmojiInput): Value =
        ObjectValue(
          List(
            "awardableId" -> implicitly[ArgEncoder[String]].encode(value.awardableId),
            "name" -> implicitly[ArgEncoder[String]].encode(value.name),
            "clientMutationId" -> value.clientMutationId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value))
          )
        )

      override def typeName: String = "AddAwardEmojiInput"
    }

  }

  case class AddProjectToSecurityDashboardInput(id: String, clientMutationId: Option[String] = None)

  object AddProjectToSecurityDashboardInput {

    implicit val encoder: ArgEncoder[AddProjectToSecurityDashboardInput] = new ArgEncoder[AddProjectToSecurityDashboardInput] {

      override def encode(value: AddProjectToSecurityDashboardInput): Value =
        ObjectValue(
          List(
            "id" -> implicitly[ArgEncoder[String]].encode(value.id),
            "clientMutationId" -> value.clientMutationId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value))
          )
        )

      override def typeName: String = "AddProjectToSecurityDashboardInput"
    }

  }

  case class AdminSidekiqQueuesDeleteJobsInput(
    user: Option[String] = None,
    project: Option[String] = None,
    rootNamespace: Option[String] = None,
    subscriptionPlan: Option[String] = None,
    callerId: Option[String] = None,
    relatedClass: Option[String] = None,
    queueName: String,
    clientMutationId: Option[String] = None
  )

  object AdminSidekiqQueuesDeleteJobsInput {

    implicit val encoder: ArgEncoder[AdminSidekiqQueuesDeleteJobsInput] = new ArgEncoder[AdminSidekiqQueuesDeleteJobsInput] {

      override def encode(value: AdminSidekiqQueuesDeleteJobsInput): Value =
        ObjectValue(
          List(
            "user" -> value.user.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value)),
            "project" -> value.project.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value)),
            "rootNamespace" -> value.rootNamespace.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value)),
            "subscriptionPlan" -> value.subscriptionPlan.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value)),
            "callerId" -> value.callerId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value)),
            "relatedClass" -> value.relatedClass.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value)),
            "queueName" -> implicitly[ArgEncoder[String]].encode(value.queueName),
            "clientMutationId" -> value.clientMutationId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value))
          )
        )

      override def typeName: String = "AdminSidekiqQueuesDeleteJobsInput"
    }

  }

  case class AlertSetAssigneesInput(
    projectPath: String,
    iid: String,
    assigneeUsernames: List[String] = Nil,
    operationMode: Option[MutationOperationMode] = None,
    clientMutationId: Option[String] = None
  )

  object AlertSetAssigneesInput {

    implicit val encoder: ArgEncoder[AlertSetAssigneesInput] = new ArgEncoder[AlertSetAssigneesInput] {

      override def encode(value: AlertSetAssigneesInput): Value =
        ObjectValue(
          List(
            "projectPath" -> implicitly[ArgEncoder[String]].encode(value.projectPath),
            "iid" -> implicitly[ArgEncoder[String]].encode(value.iid),
            "assigneeUsernames" -> ListValue(value.assigneeUsernames.map(value => implicitly[ArgEncoder[String]].encode(value))),
            "operationMode" -> value
              .operationMode
              .fold(NullValue: Value)(value => implicitly[ArgEncoder[MutationOperationMode]].encode(value)),
            "clientMutationId" -> value.clientMutationId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value))
          )
        )

      override def typeName: String = "AlertSetAssigneesInput"
    }

  }

  case class AlertTodoCreateInput(projectPath: String, iid: String, clientMutationId: Option[String] = None)

  object AlertTodoCreateInput {

    implicit val encoder: ArgEncoder[AlertTodoCreateInput] = new ArgEncoder[AlertTodoCreateInput] {

      override def encode(value: AlertTodoCreateInput): Value =
        ObjectValue(
          List(
            "projectPath" -> implicitly[ArgEncoder[String]].encode(value.projectPath),
            "iid" -> implicitly[ArgEncoder[String]].encode(value.iid),
            "clientMutationId" -> value.clientMutationId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value))
          )
        )

      override def typeName: String = "AlertTodoCreateInput"
    }

  }

  case class AwardEmojiAddInput(awardableId: String, name: String, clientMutationId: Option[String] = None)

  object AwardEmojiAddInput {

    implicit val encoder: ArgEncoder[AwardEmojiAddInput] = new ArgEncoder[AwardEmojiAddInput] {

      override def encode(value: AwardEmojiAddInput): Value =
        ObjectValue(
          List(
            "awardableId" -> implicitly[ArgEncoder[String]].encode(value.awardableId),
            "name" -> implicitly[ArgEncoder[String]].encode(value.name),
            "clientMutationId" -> value.clientMutationId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value))
          )
        )

      override def typeName: String = "AwardEmojiAddInput"
    }

  }

  case class AwardEmojiRemoveInput(awardableId: String, name: String, clientMutationId: Option[String] = None)

  object AwardEmojiRemoveInput {

    implicit val encoder: ArgEncoder[AwardEmojiRemoveInput] = new ArgEncoder[AwardEmojiRemoveInput] {

      override def encode(value: AwardEmojiRemoveInput): Value =
        ObjectValue(
          List(
            "awardableId" -> implicitly[ArgEncoder[String]].encode(value.awardableId),
            "name" -> implicitly[ArgEncoder[String]].encode(value.name),
            "clientMutationId" -> value.clientMutationId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value))
          )
        )

      override def typeName: String = "AwardEmojiRemoveInput"
    }

  }

  case class AwardEmojiToggleInput(awardableId: String, name: String, clientMutationId: Option[String] = None)

  object AwardEmojiToggleInput {

    implicit val encoder: ArgEncoder[AwardEmojiToggleInput] = new ArgEncoder[AwardEmojiToggleInput] {

      override def encode(value: AwardEmojiToggleInput): Value =
        ObjectValue(
          List(
            "awardableId" -> implicitly[ArgEncoder[String]].encode(value.awardableId),
            "name" -> implicitly[ArgEncoder[String]].encode(value.name),
            "clientMutationId" -> value.clientMutationId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value))
          )
        )

      override def typeName: String = "AwardEmojiToggleInput"
    }

  }

  case class BoardListUpdateLimitMetricsInput(
    listId: String,
    limitMetric: Option[ListLimitMetric] = None,
    maxIssueCount: Option[Int] = None,
    maxIssueWeight: Option[Int] = None,
    clientMutationId: Option[String] = None
  )

  object BoardListUpdateLimitMetricsInput {

    implicit val encoder: ArgEncoder[BoardListUpdateLimitMetricsInput] = new ArgEncoder[BoardListUpdateLimitMetricsInput] {

      override def encode(value: BoardListUpdateLimitMetricsInput): Value =
        ObjectValue(
          List(
            "listId" -> implicitly[ArgEncoder[String]].encode(value.listId),
            "limitMetric" -> value.limitMetric.fold(NullValue: Value)(value => implicitly[ArgEncoder[ListLimitMetric]].encode(value)),
            "maxIssueCount" -> value.maxIssueCount.fold(NullValue: Value)(value => implicitly[ArgEncoder[Int]].encode(value)),
            "maxIssueWeight" -> value.maxIssueWeight.fold(NullValue: Value)(value => implicitly[ArgEncoder[Int]].encode(value)),
            "clientMutationId" -> value.clientMutationId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value))
          )
        )

      override def typeName: String = "BoardListUpdateLimitMetricsInput"
    }

  }

  case class CommitAction(
    action: CommitActionMode,
    filePath: String,
    content: Option[String] = None,
    previousPath: Option[String] = None,
    lastCommitId: Option[String] = None,
    executeFilemode: Option[Boolean] = None,
    encoding: Option[CommitEncoding] = None
  )

  object CommitAction {

    implicit val encoder: ArgEncoder[CommitAction] = new ArgEncoder[CommitAction] {

      override def encode(value: CommitAction): Value =
        ObjectValue(
          List(
            "action" -> implicitly[ArgEncoder[CommitActionMode]].encode(value.action),
            "filePath" -> implicitly[ArgEncoder[String]].encode(value.filePath),
            "content" -> value.content.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value)),
            "previousPath" -> value.previousPath.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value)),
            "lastCommitId" -> value.lastCommitId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value)),
            "executeFilemode" -> value.executeFilemode.fold(NullValue: Value)(value => implicitly[ArgEncoder[Boolean]].encode(value)),
            "encoding" -> value.encoding.fold(NullValue: Value)(value => implicitly[ArgEncoder[CommitEncoding]].encode(value))
          )
        )

      override def typeName: String = "CommitAction"
    }

  }

  case class CommitCreateInput(
    projectPath: String,
    branch: String,
    message: String,
    actions: List[CommitAction] = Nil,
    clientMutationId: Option[String] = None
  )

  object CommitCreateInput {

    implicit val encoder: ArgEncoder[CommitCreateInput] = new ArgEncoder[CommitCreateInput] {

      override def encode(value: CommitCreateInput): Value =
        ObjectValue(
          List(
            "projectPath" -> implicitly[ArgEncoder[String]].encode(value.projectPath),
            "branch" -> implicitly[ArgEncoder[String]].encode(value.branch),
            "message" -> implicitly[ArgEncoder[String]].encode(value.message),
            "actions" -> ListValue(value.actions.map(value => implicitly[ArgEncoder[CommitAction]].encode(value))),
            "clientMutationId" -> value.clientMutationId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value))
          )
        )

      override def typeName: String = "CommitCreateInput"
    }

  }

  case class CreateAlertIssueInput(projectPath: String, iid: String, clientMutationId: Option[String] = None)

  object CreateAlertIssueInput {

    implicit val encoder: ArgEncoder[CreateAlertIssueInput] = new ArgEncoder[CreateAlertIssueInput] {

      override def encode(value: CreateAlertIssueInput): Value =
        ObjectValue(
          List(
            "projectPath" -> implicitly[ArgEncoder[String]].encode(value.projectPath),
            "iid" -> implicitly[ArgEncoder[String]].encode(value.iid),
            "clientMutationId" -> value.clientMutationId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value))
          )
        )

      override def typeName: String = "CreateAlertIssueInput"
    }

  }

  case class CreateAnnotationInput(
    environmentId: Option[String] = None,
    clusterId: Option[String] = None,
    startingAt: Time,
    endingAt: Option[Time] = None,
    dashboardPath: String,
    description: String,
    clientMutationId: Option[String] = None
  )

  object CreateAnnotationInput {

    implicit val encoder: ArgEncoder[CreateAnnotationInput] = new ArgEncoder[CreateAnnotationInput] {

      override def encode(value: CreateAnnotationInput): Value =
        ObjectValue(
          List(
            "environmentId" -> value.environmentId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value)),
            "clusterId" -> value.clusterId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value)),
            "startingAt" -> implicitly[ArgEncoder[Time]].encode(value.startingAt),
            "endingAt" -> value.endingAt.fold(NullValue: Value)(value => implicitly[ArgEncoder[Time]].encode(value)),
            "dashboardPath" -> implicitly[ArgEncoder[String]].encode(value.dashboardPath),
            "description" -> implicitly[ArgEncoder[String]].encode(value.description),
            "clientMutationId" -> value.clientMutationId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value))
          )
        )

      override def typeName: String = "CreateAnnotationInput"
    }

  }

  case class CreateBranchInput(projectPath: String, name: String, ref: String, clientMutationId: Option[String] = None)

  object CreateBranchInput {

    implicit val encoder: ArgEncoder[CreateBranchInput] = new ArgEncoder[CreateBranchInput] {

      override def encode(value: CreateBranchInput): Value =
        ObjectValue(
          List(
            "projectPath" -> implicitly[ArgEncoder[String]].encode(value.projectPath),
            "name" -> implicitly[ArgEncoder[String]].encode(value.name),
            "ref" -> implicitly[ArgEncoder[String]].encode(value.ref),
            "clientMutationId" -> value.clientMutationId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value))
          )
        )

      override def typeName: String = "CreateBranchInput"
    }

  }

  case class CreateDiffNoteInput(
    noteableId: String,
    body: String,
    confidential: Option[Boolean] = None,
    position: DiffPositionInput,
    clientMutationId: Option[String] = None
  )

  object CreateDiffNoteInput {

    implicit val encoder: ArgEncoder[CreateDiffNoteInput] = new ArgEncoder[CreateDiffNoteInput] {

      override def encode(value: CreateDiffNoteInput): Value =
        ObjectValue(
          List(
            "noteableId" -> implicitly[ArgEncoder[String]].encode(value.noteableId),
            "body" -> implicitly[ArgEncoder[String]].encode(value.body),
            "confidential" -> value.confidential.fold(NullValue: Value)(value => implicitly[ArgEncoder[Boolean]].encode(value)),
            "position" -> implicitly[ArgEncoder[DiffPositionInput]].encode(value.position),
            "clientMutationId" -> value.clientMutationId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value))
          )
        )

      override def typeName: String = "CreateDiffNoteInput"
    }

  }

  case class CreateEpicInput(
    groupPath: String,
    title: Option[String] = None,
    description: Option[String] = None,
    confidential: Option[Boolean] = None,
    startDateFixed: Option[String] = None,
    dueDateFixed: Option[String] = None,
    startDateIsFixed: Option[Boolean] = None,
    dueDateIsFixed: Option[Boolean] = None,
    addLabelIds: Option[List[String]] = None,
    removeLabelIds: Option[List[String]] = None,
    clientMutationId: Option[String] = None
  )

  object CreateEpicInput {

    implicit val encoder: ArgEncoder[CreateEpicInput] = new ArgEncoder[CreateEpicInput] {

      override def encode(value: CreateEpicInput): Value =
        ObjectValue(
          List(
            "groupPath" -> implicitly[ArgEncoder[String]].encode(value.groupPath),
            "title" -> value.title.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value)),
            "description" -> value.description.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value)),
            "confidential" -> value.confidential.fold(NullValue: Value)(value => implicitly[ArgEncoder[Boolean]].encode(value)),
            "startDateFixed" -> value.startDateFixed.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value)),
            "dueDateFixed" -> value.dueDateFixed.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value)),
            "startDateIsFixed" -> value.startDateIsFixed.fold(NullValue: Value)(value => implicitly[ArgEncoder[Boolean]].encode(value)),
            "dueDateIsFixed" -> value.dueDateIsFixed.fold(NullValue: Value)(value => implicitly[ArgEncoder[Boolean]].encode(value)),
            "addLabelIds" -> value
              .addLabelIds
              .fold(NullValue: Value)(value => ListValue(value.map(value => implicitly[ArgEncoder[String]].encode(value)))),
            "removeLabelIds" -> value
              .removeLabelIds
              .fold(NullValue: Value)(value => ListValue(value.map(value => implicitly[ArgEncoder[String]].encode(value)))),
            "clientMutationId" -> value.clientMutationId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value))
          )
        )

      override def typeName: String = "CreateEpicInput"
    }

  }

  case class CreateImageDiffNoteInput(
    noteableId: String,
    body: String,
    confidential: Option[Boolean] = None,
    position: DiffImagePositionInput,
    clientMutationId: Option[String] = None
  )

  object CreateImageDiffNoteInput {

    implicit val encoder: ArgEncoder[CreateImageDiffNoteInput] = new ArgEncoder[CreateImageDiffNoteInput] {

      override def encode(value: CreateImageDiffNoteInput): Value =
        ObjectValue(
          List(
            "noteableId" -> implicitly[ArgEncoder[String]].encode(value.noteableId),
            "body" -> implicitly[ArgEncoder[String]].encode(value.body),
            "confidential" -> value.confidential.fold(NullValue: Value)(value => implicitly[ArgEncoder[Boolean]].encode(value)),
            "position" -> implicitly[ArgEncoder[DiffImagePositionInput]].encode(value.position),
            "clientMutationId" -> value.clientMutationId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value))
          )
        )

      override def typeName: String = "CreateImageDiffNoteInput"
    }

  }

  case class CreateIterationInput(
    groupPath: String,
    title: Option[String] = None,
    description: Option[String] = None,
    startDate: Option[String] = None,
    dueDate: Option[String] = None,
    clientMutationId: Option[String] = None
  )

  object CreateIterationInput {

    implicit val encoder: ArgEncoder[CreateIterationInput] = new ArgEncoder[CreateIterationInput] {

      override def encode(value: CreateIterationInput): Value =
        ObjectValue(
          List(
            "groupPath" -> implicitly[ArgEncoder[String]].encode(value.groupPath),
            "title" -> value.title.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value)),
            "description" -> value.description.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value)),
            "startDate" -> value.startDate.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value)),
            "dueDate" -> value.dueDate.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value)),
            "clientMutationId" -> value.clientMutationId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value))
          )
        )

      override def typeName: String = "CreateIterationInput"
    }

  }

  case class CreateNoteInput(
    noteableId: String,
    body: String,
    confidential: Option[Boolean] = None,
    discussionId: Option[String] = None,
    clientMutationId: Option[String] = None
  )

  object CreateNoteInput {

    implicit val encoder: ArgEncoder[CreateNoteInput] = new ArgEncoder[CreateNoteInput] {

      override def encode(value: CreateNoteInput): Value =
        ObjectValue(
          List(
            "noteableId" -> implicitly[ArgEncoder[String]].encode(value.noteableId),
            "body" -> implicitly[ArgEncoder[String]].encode(value.body),
            "confidential" -> value.confidential.fold(NullValue: Value)(value => implicitly[ArgEncoder[Boolean]].encode(value)),
            "discussionId" -> value.discussionId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value)),
            "clientMutationId" -> value.clientMutationId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value))
          )
        )

      override def typeName: String = "CreateNoteInput"
    }

  }

  case class CreateRequirementInput(title: String, projectPath: String, clientMutationId: Option[String] = None)

  object CreateRequirementInput {

    implicit val encoder: ArgEncoder[CreateRequirementInput] = new ArgEncoder[CreateRequirementInput] {

      override def encode(value: CreateRequirementInput): Value =
        ObjectValue(
          List(
            "title" -> implicitly[ArgEncoder[String]].encode(value.title),
            "projectPath" -> implicitly[ArgEncoder[String]].encode(value.projectPath),
            "clientMutationId" -> value.clientMutationId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value))
          )
        )

      override def typeName: String = "CreateRequirementInput"
    }

  }

  case class CreateSnippetInput(
    title: String,
    fileName: Option[String] = None,
    content: Option[String] = None,
    description: Option[String] = None,
    visibilityLevel: VisibilityLevelsEnum,
    projectPath: Option[String] = None,
    uploadedFiles: Option[List[String]] = None,
    blobActions: Option[List[SnippetBlobActionInputType]] = None,
    clientMutationId: Option[String] = None
  )

  object CreateSnippetInput {

    implicit val encoder: ArgEncoder[CreateSnippetInput] = new ArgEncoder[CreateSnippetInput] {

      override def encode(value: CreateSnippetInput): Value =
        ObjectValue(
          List(
            "title" -> implicitly[ArgEncoder[String]].encode(value.title),
            "fileName" -> value.fileName.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value)),
            "content" -> value.content.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value)),
            "description" -> value.description.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value)),
            "visibilityLevel" -> implicitly[ArgEncoder[VisibilityLevelsEnum]].encode(value.visibilityLevel),
            "projectPath" -> value.projectPath.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value)),
            "uploadedFiles" -> value
              .uploadedFiles
              .fold(NullValue: Value)(value => ListValue(value.map(value => implicitly[ArgEncoder[String]].encode(value)))),
            "blobActions" -> value
              .blobActions
              .fold(NullValue: Value)(value =>
                ListValue(value.map(value => implicitly[ArgEncoder[SnippetBlobActionInputType]].encode(value)))
              ),
            "clientMutationId" -> value.clientMutationId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value))
          )
        )

      override def typeName: String = "CreateSnippetInput"
    }

  }

  case class DastSiteProfileCreateInput(
    fullPath: String,
    profileName: String,
    targetUrl: Option[String] = None,
    clientMutationId: Option[String] = None
  )

  object DastSiteProfileCreateInput {

    implicit val encoder: ArgEncoder[DastSiteProfileCreateInput] = new ArgEncoder[DastSiteProfileCreateInput] {

      override def encode(value: DastSiteProfileCreateInput): Value =
        ObjectValue(
          List(
            "fullPath" -> implicitly[ArgEncoder[String]].encode(value.fullPath),
            "profileName" -> implicitly[ArgEncoder[String]].encode(value.profileName),
            "targetUrl" -> value.targetUrl.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value)),
            "clientMutationId" -> value.clientMutationId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value))
          )
        )

      override def typeName: String = "DastSiteProfileCreateInput"
    }

  }

  case class DeleteAnnotationInput(id: String, clientMutationId: Option[String] = None)

  object DeleteAnnotationInput {

    implicit val encoder: ArgEncoder[DeleteAnnotationInput] = new ArgEncoder[DeleteAnnotationInput] {

      override def encode(value: DeleteAnnotationInput): Value =
        ObjectValue(
          List(
            "id" -> implicitly[ArgEncoder[String]].encode(value.id),
            "clientMutationId" -> value.clientMutationId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value))
          )
        )

      override def typeName: String = "DeleteAnnotationInput"
    }

  }

  case class DesignManagementDeleteInput(
    projectPath: String,
    iid: String,
    filenames: List[String] = Nil,
    clientMutationId: Option[String] = None
  )

  object DesignManagementDeleteInput {

    implicit val encoder: ArgEncoder[DesignManagementDeleteInput] = new ArgEncoder[DesignManagementDeleteInput] {

      override def encode(value: DesignManagementDeleteInput): Value =
        ObjectValue(
          List(
            "projectPath" -> implicitly[ArgEncoder[String]].encode(value.projectPath),
            "iid" -> implicitly[ArgEncoder[String]].encode(value.iid),
            "filenames" -> ListValue(value.filenames.map(value => implicitly[ArgEncoder[String]].encode(value))),
            "clientMutationId" -> value.clientMutationId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value))
          )
        )

      override def typeName: String = "DesignManagementDeleteInput"
    }

  }

  case class DesignManagementUploadInput(
    projectPath: String,
    iid: String,
    files: List[Upload] = Nil,
    clientMutationId: Option[String] = None
  )

  object DesignManagementUploadInput {

    implicit val encoder: ArgEncoder[DesignManagementUploadInput] = new ArgEncoder[DesignManagementUploadInput] {

      override def encode(value: DesignManagementUploadInput): Value =
        ObjectValue(
          List(
            "projectPath" -> implicitly[ArgEncoder[String]].encode(value.projectPath),
            "iid" -> implicitly[ArgEncoder[String]].encode(value.iid),
            "files" -> ListValue(value.files.map(value => implicitly[ArgEncoder[Upload]].encode(value))),
            "clientMutationId" -> value.clientMutationId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value))
          )
        )

      override def typeName: String = "DesignManagementUploadInput"
    }

  }

  case class DestroyNoteInput(id: String, clientMutationId: Option[String] = None)

  object DestroyNoteInput {

    implicit val encoder: ArgEncoder[DestroyNoteInput] = new ArgEncoder[DestroyNoteInput] {

      override def encode(value: DestroyNoteInput): Value =
        ObjectValue(
          List(
            "id" -> implicitly[ArgEncoder[String]].encode(value.id),
            "clientMutationId" -> value.clientMutationId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value))
          )
        )

      override def typeName: String = "DestroyNoteInput"
    }

  }

  case class DestroySnippetInput(id: String, clientMutationId: Option[String] = None)

  object DestroySnippetInput {

    implicit val encoder: ArgEncoder[DestroySnippetInput] = new ArgEncoder[DestroySnippetInput] {

      override def encode(value: DestroySnippetInput): Value =
        ObjectValue(
          List(
            "id" -> implicitly[ArgEncoder[String]].encode(value.id),
            "clientMutationId" -> value.clientMutationId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value))
          )
        )

      override def typeName: String = "DestroySnippetInput"
    }

  }

  case class DiffImagePositionInput(
    headSha: String,
    baseSha: Option[String] = None,
    startSha: String,
    paths: DiffPathsInput,
    x: Int,
    y: Int,
    width: Int,
    height: Int
  )

  object DiffImagePositionInput {

    implicit val encoder: ArgEncoder[DiffImagePositionInput] = new ArgEncoder[DiffImagePositionInput] {

      override def encode(value: DiffImagePositionInput): Value =
        ObjectValue(
          List(
            "headSha" -> implicitly[ArgEncoder[String]].encode(value.headSha),
            "baseSha" -> value.baseSha.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value)),
            "startSha" -> implicitly[ArgEncoder[String]].encode(value.startSha),
            "paths" -> implicitly[ArgEncoder[DiffPathsInput]].encode(value.paths),
            "x" -> implicitly[ArgEncoder[Int]].encode(value.x),
            "y" -> implicitly[ArgEncoder[Int]].encode(value.y),
            "width" -> implicitly[ArgEncoder[Int]].encode(value.width),
            "height" -> implicitly[ArgEncoder[Int]].encode(value.height)
          )
        )

      override def typeName: String = "DiffImagePositionInput"
    }

  }

  case class DiffPathsInput(oldPath: Option[String] = None, newPath: Option[String] = None)

  object DiffPathsInput {

    implicit val encoder: ArgEncoder[DiffPathsInput] = new ArgEncoder[DiffPathsInput] {

      override def encode(value: DiffPathsInput): Value =
        ObjectValue(
          List(
            "oldPath" -> value.oldPath.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value)),
            "newPath" -> value.newPath.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value))
          )
        )

      override def typeName: String = "DiffPathsInput"
    }

  }

  case class DiffPositionInput(
    headSha: String,
    baseSha: Option[String] = None,
    startSha: String,
    paths: DiffPathsInput,
    oldLine: Option[Int] = None,
    newLine: Int
  )

  object DiffPositionInput {

    implicit val encoder: ArgEncoder[DiffPositionInput] = new ArgEncoder[DiffPositionInput] {

      override def encode(value: DiffPositionInput): Value =
        ObjectValue(
          List(
            "headSha" -> implicitly[ArgEncoder[String]].encode(value.headSha),
            "baseSha" -> value.baseSha.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value)),
            "startSha" -> implicitly[ArgEncoder[String]].encode(value.startSha),
            "paths" -> implicitly[ArgEncoder[DiffPathsInput]].encode(value.paths),
            "oldLine" -> value.oldLine.fold(NullValue: Value)(value => implicitly[ArgEncoder[Int]].encode(value)),
            "newLine" -> implicitly[ArgEncoder[Int]].encode(value.newLine)
          )
        )

      override def typeName: String = "DiffPositionInput"
    }

  }

  case class DiscussionToggleResolveInput(id: String, resolve: Boolean, clientMutationId: Option[String] = None)

  object DiscussionToggleResolveInput {

    implicit val encoder: ArgEncoder[DiscussionToggleResolveInput] = new ArgEncoder[DiscussionToggleResolveInput] {

      override def encode(value: DiscussionToggleResolveInput): Value =
        ObjectValue(
          List(
            "id" -> implicitly[ArgEncoder[String]].encode(value.id),
            "resolve" -> implicitly[ArgEncoder[Boolean]].encode(value.resolve),
            "clientMutationId" -> value.clientMutationId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value))
          )
        )

      override def typeName: String = "DiscussionToggleResolveInput"
    }

  }

  case class DismissVulnerabilityInput(id: String, comment: Option[String] = None, clientMutationId: Option[String] = None)

  object DismissVulnerabilityInput {

    implicit val encoder: ArgEncoder[DismissVulnerabilityInput] = new ArgEncoder[DismissVulnerabilityInput] {

      override def encode(value: DismissVulnerabilityInput): Value =
        ObjectValue(
          List(
            "id" -> implicitly[ArgEncoder[String]].encode(value.id),
            "comment" -> value.comment.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value)),
            "clientMutationId" -> value.clientMutationId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value))
          )
        )

      override def typeName: String = "DismissVulnerabilityInput"
    }

  }

  case class EpicAddIssueInput(
    iid: String,
    groupPath: String,
    projectPath: String,
    issueIid: String,
    clientMutationId: Option[String] = None
  )

  object EpicAddIssueInput {

    implicit val encoder: ArgEncoder[EpicAddIssueInput] = new ArgEncoder[EpicAddIssueInput] {

      override def encode(value: EpicAddIssueInput): Value =
        ObjectValue(
          List(
            "iid" -> implicitly[ArgEncoder[String]].encode(value.iid),
            "groupPath" -> implicitly[ArgEncoder[String]].encode(value.groupPath),
            "projectPath" -> implicitly[ArgEncoder[String]].encode(value.projectPath),
            "issueIid" -> implicitly[ArgEncoder[String]].encode(value.issueIid),
            "clientMutationId" -> value.clientMutationId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value))
          )
        )

      override def typeName: String = "EpicAddIssueInput"
    }

  }

  case class EpicSetSubscriptionInput(iid: String, groupPath: String, subscribedState: Boolean, clientMutationId: Option[String] = None)

  object EpicSetSubscriptionInput {

    implicit val encoder: ArgEncoder[EpicSetSubscriptionInput] = new ArgEncoder[EpicSetSubscriptionInput] {

      override def encode(value: EpicSetSubscriptionInput): Value =
        ObjectValue(
          List(
            "iid" -> implicitly[ArgEncoder[String]].encode(value.iid),
            "groupPath" -> implicitly[ArgEncoder[String]].encode(value.groupPath),
            "subscribedState" -> implicitly[ArgEncoder[Boolean]].encode(value.subscribedState),
            "clientMutationId" -> value.clientMutationId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value))
          )
        )

      override def typeName: String = "EpicSetSubscriptionInput"
    }

  }

  case class EpicTreeNodeFieldsInputType(
    id: String,
    adjacentReferenceId: Option[String] = None,
    relativePosition: Option[MoveType] = None,
    newParentId: Option[String] = None
  )

  object EpicTreeNodeFieldsInputType {

    implicit val encoder: ArgEncoder[EpicTreeNodeFieldsInputType] = new ArgEncoder[EpicTreeNodeFieldsInputType] {

      override def encode(value: EpicTreeNodeFieldsInputType): Value =
        ObjectValue(
          List(
            "id" -> implicitly[ArgEncoder[String]].encode(value.id),
            "adjacentReferenceId" -> value
              .adjacentReferenceId
              .fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value)),
            "relativePosition" -> value.relativePosition.fold(NullValue: Value)(value => implicitly[ArgEncoder[MoveType]].encode(value)),
            "newParentId" -> value.newParentId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value))
          )
        )

      override def typeName: String = "EpicTreeNodeFieldsInputType"
    }

  }

  case class EpicTreeReorderInput(baseEpicId: String, moved: EpicTreeNodeFieldsInputType, clientMutationId: Option[String] = None)

  object EpicTreeReorderInput {

    implicit val encoder: ArgEncoder[EpicTreeReorderInput] = new ArgEncoder[EpicTreeReorderInput] {

      override def encode(value: EpicTreeReorderInput): Value =
        ObjectValue(
          List(
            "baseEpicId" -> implicitly[ArgEncoder[String]].encode(value.baseEpicId),
            "moved" -> implicitly[ArgEncoder[EpicTreeNodeFieldsInputType]].encode(value.moved),
            "clientMutationId" -> value.clientMutationId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value))
          )
        )

      override def typeName: String = "EpicTreeReorderInput"
    }

  }

  case class IssueSetConfidentialInput(projectPath: String, iid: String, confidential: Boolean, clientMutationId: Option[String] = None)

  object IssueSetConfidentialInput {

    implicit val encoder: ArgEncoder[IssueSetConfidentialInput] = new ArgEncoder[IssueSetConfidentialInput] {

      override def encode(value: IssueSetConfidentialInput): Value =
        ObjectValue(
          List(
            "projectPath" -> implicitly[ArgEncoder[String]].encode(value.projectPath),
            "iid" -> implicitly[ArgEncoder[String]].encode(value.iid),
            "confidential" -> implicitly[ArgEncoder[Boolean]].encode(value.confidential),
            "clientMutationId" -> value.clientMutationId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value))
          )
        )

      override def typeName: String = "IssueSetConfidentialInput"
    }

  }

  case class IssueSetDueDateInput(projectPath: String, iid: String, dueDate: Time, clientMutationId: Option[String] = None)

  object IssueSetDueDateInput {

    implicit val encoder: ArgEncoder[IssueSetDueDateInput] = new ArgEncoder[IssueSetDueDateInput] {

      override def encode(value: IssueSetDueDateInput): Value =
        ObjectValue(
          List(
            "projectPath" -> implicitly[ArgEncoder[String]].encode(value.projectPath),
            "iid" -> implicitly[ArgEncoder[String]].encode(value.iid),
            "dueDate" -> implicitly[ArgEncoder[Time]].encode(value.dueDate),
            "clientMutationId" -> value.clientMutationId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value))
          )
        )

      override def typeName: String = "IssueSetDueDateInput"
    }

  }

  case class IssueSetIterationInput(
    projectPath: String,
    iid: String,
    iterationId: Option[String] = None,
    clientMutationId: Option[String] = None
  )

  object IssueSetIterationInput {

    implicit val encoder: ArgEncoder[IssueSetIterationInput] = new ArgEncoder[IssueSetIterationInput] {

      override def encode(value: IssueSetIterationInput): Value =
        ObjectValue(
          List(
            "projectPath" -> implicitly[ArgEncoder[String]].encode(value.projectPath),
            "iid" -> implicitly[ArgEncoder[String]].encode(value.iid),
            "iterationId" -> value.iterationId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value)),
            "clientMutationId" -> value.clientMutationId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value))
          )
        )

      override def typeName: String = "IssueSetIterationInput"
    }

  }

  case class IssueSetLockedInput(projectPath: String, iid: String, locked: Boolean, clientMutationId: Option[String] = None)

  object IssueSetLockedInput {

    implicit val encoder: ArgEncoder[IssueSetLockedInput] = new ArgEncoder[IssueSetLockedInput] {

      override def encode(value: IssueSetLockedInput): Value =
        ObjectValue(
          List(
            "projectPath" -> implicitly[ArgEncoder[String]].encode(value.projectPath),
            "iid" -> implicitly[ArgEncoder[String]].encode(value.iid),
            "locked" -> implicitly[ArgEncoder[Boolean]].encode(value.locked),
            "clientMutationId" -> value.clientMutationId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value))
          )
        )

      override def typeName: String = "IssueSetLockedInput"
    }

  }

  case class IssueSetWeightInput(projectPath: String, iid: String, weight: Int, clientMutationId: Option[String] = None)

  object IssueSetWeightInput {

    implicit val encoder: ArgEncoder[IssueSetWeightInput] = new ArgEncoder[IssueSetWeightInput] {

      override def encode(value: IssueSetWeightInput): Value =
        ObjectValue(
          List(
            "projectPath" -> implicitly[ArgEncoder[String]].encode(value.projectPath),
            "iid" -> implicitly[ArgEncoder[String]].encode(value.iid),
            "weight" -> implicitly[ArgEncoder[Int]].encode(value.weight),
            "clientMutationId" -> value.clientMutationId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value))
          )
        )

      override def typeName: String = "IssueSetWeightInput"
    }

  }

  case class JiraImportStartInput(
    projectPath: String,
    jiraProjectKey: String,
    jiraProjectName: Option[String] = None,
    usersMapping: Option[List[JiraUsersMappingInputType]] = None,
    clientMutationId: Option[String] = None
  )

  object JiraImportStartInput {

    implicit val encoder: ArgEncoder[JiraImportStartInput] = new ArgEncoder[JiraImportStartInput] {

      override def encode(value: JiraImportStartInput): Value =
        ObjectValue(
          List(
            "projectPath" -> implicitly[ArgEncoder[String]].encode(value.projectPath),
            "jiraProjectKey" -> implicitly[ArgEncoder[String]].encode(value.jiraProjectKey),
            "jiraProjectName" -> value.jiraProjectName.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value)),
            "usersMapping" -> value
              .usersMapping
              .fold(NullValue: Value)(value =>
                ListValue(value.map(value => implicitly[ArgEncoder[JiraUsersMappingInputType]].encode(value)))
              ),
            "clientMutationId" -> value.clientMutationId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value))
          )
        )

      override def typeName: String = "JiraImportStartInput"
    }

  }

  case class JiraImportUsersInput(projectPath: String, startAt: Option[Int] = None, clientMutationId: Option[String] = None)

  object JiraImportUsersInput {

    implicit val encoder: ArgEncoder[JiraImportUsersInput] = new ArgEncoder[JiraImportUsersInput] {

      override def encode(value: JiraImportUsersInput): Value =
        ObjectValue(
          List(
            "projectPath" -> implicitly[ArgEncoder[String]].encode(value.projectPath),
            "startAt" -> value.startAt.fold(NullValue: Value)(value => implicitly[ArgEncoder[Int]].encode(value)),
            "clientMutationId" -> value.clientMutationId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value))
          )
        )

      override def typeName: String = "JiraImportUsersInput"
    }

  }

  case class JiraUsersMappingInputType(jiraAccountId: String, gitlabId: Option[Int] = None)

  object JiraUsersMappingInputType {

    implicit val encoder: ArgEncoder[JiraUsersMappingInputType] = new ArgEncoder[JiraUsersMappingInputType] {

      override def encode(value: JiraUsersMappingInputType): Value =
        ObjectValue(
          List(
            "jiraAccountId" -> implicitly[ArgEncoder[String]].encode(value.jiraAccountId),
            "gitlabId" -> value.gitlabId.fold(NullValue: Value)(value => implicitly[ArgEncoder[Int]].encode(value))
          )
        )

      override def typeName: String = "JiraUsersMappingInputType"
    }

  }

  case class MarkAsSpamSnippetInput(id: String, clientMutationId: Option[String] = None)

  object MarkAsSpamSnippetInput {

    implicit val encoder: ArgEncoder[MarkAsSpamSnippetInput] = new ArgEncoder[MarkAsSpamSnippetInput] {

      override def encode(value: MarkAsSpamSnippetInput): Value =
        ObjectValue(
          List(
            "id" -> implicitly[ArgEncoder[String]].encode(value.id),
            "clientMutationId" -> value.clientMutationId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value))
          )
        )

      override def typeName: String = "MarkAsSpamSnippetInput"
    }

  }

  case class MergeRequestCreateInput(
    projectPath: String,
    title: String,
    sourceBranch: String,
    targetBranch: String,
    description: Option[String] = None,
    labels: Option[List[String]] = None,
    clientMutationId: Option[String] = None
  )

  object MergeRequestCreateInput {

    implicit val encoder: ArgEncoder[MergeRequestCreateInput] = new ArgEncoder[MergeRequestCreateInput] {

      override def encode(value: MergeRequestCreateInput): Value =
        ObjectValue(
          List(
            "projectPath" -> implicitly[ArgEncoder[String]].encode(value.projectPath),
            "title" -> implicitly[ArgEncoder[String]].encode(value.title),
            "sourceBranch" -> implicitly[ArgEncoder[String]].encode(value.sourceBranch),
            "targetBranch" -> implicitly[ArgEncoder[String]].encode(value.targetBranch),
            "description" -> value.description.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value)),
            "labels" -> value
              .labels
              .fold(NullValue: Value)(value => ListValue(value.map(value => implicitly[ArgEncoder[String]].encode(value)))),
            "clientMutationId" -> value.clientMutationId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value))
          )
        )

      override def typeName: String = "MergeRequestCreateInput"
    }

  }

  case class MergeRequestSetAssigneesInput(
    projectPath: String,
    iid: String,
    assigneeUsernames: List[String] = Nil,
    operationMode: Option[MutationOperationMode] = None,
    clientMutationId: Option[String] = None
  )

  object MergeRequestSetAssigneesInput {

    implicit val encoder: ArgEncoder[MergeRequestSetAssigneesInput] = new ArgEncoder[MergeRequestSetAssigneesInput] {

      override def encode(value: MergeRequestSetAssigneesInput): Value =
        ObjectValue(
          List(
            "projectPath" -> implicitly[ArgEncoder[String]].encode(value.projectPath),
            "iid" -> implicitly[ArgEncoder[String]].encode(value.iid),
            "assigneeUsernames" -> ListValue(value.assigneeUsernames.map(value => implicitly[ArgEncoder[String]].encode(value))),
            "operationMode" -> value
              .operationMode
              .fold(NullValue: Value)(value => implicitly[ArgEncoder[MutationOperationMode]].encode(value)),
            "clientMutationId" -> value.clientMutationId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value))
          )
        )

      override def typeName: String = "MergeRequestSetAssigneesInput"
    }

  }

  case class MergeRequestSetLabelsInput(
    projectPath: String,
    iid: String,
    labelIds: List[String] = Nil,
    operationMode: Option[MutationOperationMode] = None,
    clientMutationId: Option[String] = None
  )

  object MergeRequestSetLabelsInput {

    implicit val encoder: ArgEncoder[MergeRequestSetLabelsInput] = new ArgEncoder[MergeRequestSetLabelsInput] {

      override def encode(value: MergeRequestSetLabelsInput): Value =
        ObjectValue(
          List(
            "projectPath" -> implicitly[ArgEncoder[String]].encode(value.projectPath),
            "iid" -> implicitly[ArgEncoder[String]].encode(value.iid),
            "labelIds" -> ListValue(value.labelIds.map(value => implicitly[ArgEncoder[String]].encode(value))),
            "operationMode" -> value
              .operationMode
              .fold(NullValue: Value)(value => implicitly[ArgEncoder[MutationOperationMode]].encode(value)),
            "clientMutationId" -> value.clientMutationId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value))
          )
        )

      override def typeName: String = "MergeRequestSetLabelsInput"
    }

  }

  case class MergeRequestSetLockedInput(projectPath: String, iid: String, locked: Boolean, clientMutationId: Option[String] = None)

  object MergeRequestSetLockedInput {

    implicit val encoder: ArgEncoder[MergeRequestSetLockedInput] = new ArgEncoder[MergeRequestSetLockedInput] {

      override def encode(value: MergeRequestSetLockedInput): Value =
        ObjectValue(
          List(
            "projectPath" -> implicitly[ArgEncoder[String]].encode(value.projectPath),
            "iid" -> implicitly[ArgEncoder[String]].encode(value.iid),
            "locked" -> implicitly[ArgEncoder[Boolean]].encode(value.locked),
            "clientMutationId" -> value.clientMutationId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value))
          )
        )

      override def typeName: String = "MergeRequestSetLockedInput"
    }

  }

  case class MergeRequestSetMilestoneInput(
    projectPath: String,
    iid: String,
    milestoneId: Option[String] = None,
    clientMutationId: Option[String] = None
  )

  object MergeRequestSetMilestoneInput {

    implicit val encoder: ArgEncoder[MergeRequestSetMilestoneInput] = new ArgEncoder[MergeRequestSetMilestoneInput] {

      override def encode(value: MergeRequestSetMilestoneInput): Value =
        ObjectValue(
          List(
            "projectPath" -> implicitly[ArgEncoder[String]].encode(value.projectPath),
            "iid" -> implicitly[ArgEncoder[String]].encode(value.iid),
            "milestoneId" -> value.milestoneId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value)),
            "clientMutationId" -> value.clientMutationId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value))
          )
        )

      override def typeName: String = "MergeRequestSetMilestoneInput"
    }

  }

  case class MergeRequestSetSubscriptionInput(
    projectPath: String,
    iid: String,
    subscribedState: Boolean,
    clientMutationId: Option[String] = None
  )

  object MergeRequestSetSubscriptionInput {

    implicit val encoder: ArgEncoder[MergeRequestSetSubscriptionInput] = new ArgEncoder[MergeRequestSetSubscriptionInput] {

      override def encode(value: MergeRequestSetSubscriptionInput): Value =
        ObjectValue(
          List(
            "projectPath" -> implicitly[ArgEncoder[String]].encode(value.projectPath),
            "iid" -> implicitly[ArgEncoder[String]].encode(value.iid),
            "subscribedState" -> implicitly[ArgEncoder[Boolean]].encode(value.subscribedState),
            "clientMutationId" -> value.clientMutationId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value))
          )
        )

      override def typeName: String = "MergeRequestSetSubscriptionInput"
    }

  }

  case class MergeRequestSetWipInput(projectPath: String, iid: String, wip: Boolean, clientMutationId: Option[String] = None)

  object MergeRequestSetWipInput {

    implicit val encoder: ArgEncoder[MergeRequestSetWipInput] = new ArgEncoder[MergeRequestSetWipInput] {

      override def encode(value: MergeRequestSetWipInput): Value =
        ObjectValue(
          List(
            "projectPath" -> implicitly[ArgEncoder[String]].encode(value.projectPath),
            "iid" -> implicitly[ArgEncoder[String]].encode(value.iid),
            "wip" -> implicitly[ArgEncoder[Boolean]].encode(value.wip),
            "clientMutationId" -> value.clientMutationId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value))
          )
        )

      override def typeName: String = "MergeRequestSetWipInput"
    }

  }

  case class MergeRequestUpdateInput(
    projectPath: String,
    iid: String,
    title: Option[String] = None,
    targetBranch: Option[String] = None,
    description: Option[String] = None,
    clientMutationId: Option[String] = None
  )

  object MergeRequestUpdateInput {

    implicit val encoder: ArgEncoder[MergeRequestUpdateInput] = new ArgEncoder[MergeRequestUpdateInput] {

      override def encode(value: MergeRequestUpdateInput): Value =
        ObjectValue(
          List(
            "projectPath" -> implicitly[ArgEncoder[String]].encode(value.projectPath),
            "iid" -> implicitly[ArgEncoder[String]].encode(value.iid),
            "title" -> value.title.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value)),
            "targetBranch" -> value.targetBranch.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value)),
            "description" -> value.description.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value)),
            "clientMutationId" -> value.clientMutationId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value))
          )
        )

      override def typeName: String = "MergeRequestUpdateInput"
    }

  }

  case class RemoveAwardEmojiInput(awardableId: String, name: String, clientMutationId: Option[String] = None)

  object RemoveAwardEmojiInput {

    implicit val encoder: ArgEncoder[RemoveAwardEmojiInput] = new ArgEncoder[RemoveAwardEmojiInput] {

      override def encode(value: RemoveAwardEmojiInput): Value =
        ObjectValue(
          List(
            "awardableId" -> implicitly[ArgEncoder[String]].encode(value.awardableId),
            "name" -> implicitly[ArgEncoder[String]].encode(value.name),
            "clientMutationId" -> value.clientMutationId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value))
          )
        )

      override def typeName: String = "RemoveAwardEmojiInput"
    }

  }

  case class RemoveProjectFromSecurityDashboardInput(id: String, clientMutationId: Option[String] = None)

  object RemoveProjectFromSecurityDashboardInput {

    implicit val encoder: ArgEncoder[RemoveProjectFromSecurityDashboardInput] = new ArgEncoder[RemoveProjectFromSecurityDashboardInput] {

      override def encode(value: RemoveProjectFromSecurityDashboardInput): Value =
        ObjectValue(
          List(
            "id" -> implicitly[ArgEncoder[String]].encode(value.id),
            "clientMutationId" -> value.clientMutationId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value))
          )
        )

      override def typeName: String = "RemoveProjectFromSecurityDashboardInput"
    }

  }

  case class RunDASTScanInput(
    projectPath: String,
    targetUrl: String,
    branch: String,
    scanType: DastScanTypeEnum,
    clientMutationId: Option[String] = None
  )

  object RunDASTScanInput {

    implicit val encoder: ArgEncoder[RunDASTScanInput] = new ArgEncoder[RunDASTScanInput] {

      override def encode(value: RunDASTScanInput): Value =
        ObjectValue(
          List(
            "projectPath" -> implicitly[ArgEncoder[String]].encode(value.projectPath),
            "targetUrl" -> implicitly[ArgEncoder[String]].encode(value.targetUrl),
            "branch" -> implicitly[ArgEncoder[String]].encode(value.branch),
            "scanType" -> implicitly[ArgEncoder[DastScanTypeEnum]].encode(value.scanType),
            "clientMutationId" -> value.clientMutationId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value))
          )
        )

      override def typeName: String = "RunDASTScanInput"
    }

  }

  case class SnippetBlobActionInputType(
    action: SnippetBlobActionEnum,
    previousPath: Option[String] = None,
    filePath: String,
    content: Option[String] = None
  )

  object SnippetBlobActionInputType {

    implicit val encoder: ArgEncoder[SnippetBlobActionInputType] = new ArgEncoder[SnippetBlobActionInputType] {

      override def encode(value: SnippetBlobActionInputType): Value =
        ObjectValue(
          List(
            "action" -> implicitly[ArgEncoder[SnippetBlobActionEnum]].encode(value.action),
            "previousPath" -> value.previousPath.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value)),
            "filePath" -> implicitly[ArgEncoder[String]].encode(value.filePath),
            "content" -> value.content.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value))
          )
        )

      override def typeName: String = "SnippetBlobActionInputType"
    }

  }

  case class TodoMarkDoneInput(id: String, clientMutationId: Option[String] = None)

  object TodoMarkDoneInput {

    implicit val encoder: ArgEncoder[TodoMarkDoneInput] = new ArgEncoder[TodoMarkDoneInput] {

      override def encode(value: TodoMarkDoneInput): Value =
        ObjectValue(
          List(
            "id" -> implicitly[ArgEncoder[String]].encode(value.id),
            "clientMutationId" -> value.clientMutationId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value))
          )
        )

      override def typeName: String = "TodoMarkDoneInput"
    }

  }

  case class TodoRestoreInput(id: String, clientMutationId: Option[String] = None)

  object TodoRestoreInput {

    implicit val encoder: ArgEncoder[TodoRestoreInput] = new ArgEncoder[TodoRestoreInput] {

      override def encode(value: TodoRestoreInput): Value =
        ObjectValue(
          List(
            "id" -> implicitly[ArgEncoder[String]].encode(value.id),
            "clientMutationId" -> value.clientMutationId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value))
          )
        )

      override def typeName: String = "TodoRestoreInput"
    }

  }

  case class TodoRestoreManyInput(ids: List[String] = Nil, clientMutationId: Option[String] = None)

  object TodoRestoreManyInput {

    implicit val encoder: ArgEncoder[TodoRestoreManyInput] = new ArgEncoder[TodoRestoreManyInput] {

      override def encode(value: TodoRestoreManyInput): Value =
        ObjectValue(
          List(
            "ids" -> ListValue(value.ids.map(value => implicitly[ArgEncoder[String]].encode(value))),
            "clientMutationId" -> value.clientMutationId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value))
          )
        )

      override def typeName: String = "TodoRestoreManyInput"
    }

  }

  case class TodosMarkAllDoneInput(clientMutationId: Option[String] = None)

  object TodosMarkAllDoneInput {

    implicit val encoder: ArgEncoder[TodosMarkAllDoneInput] = new ArgEncoder[TodosMarkAllDoneInput] {

      override def encode(value: TodosMarkAllDoneInput): Value =
        ObjectValue(
          List("clientMutationId" -> value.clientMutationId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value)))
        )

      override def typeName: String = "TodosMarkAllDoneInput"
    }

  }

  case class ToggleAwardEmojiInput(awardableId: String, name: String, clientMutationId: Option[String] = None)

  object ToggleAwardEmojiInput {

    implicit val encoder: ArgEncoder[ToggleAwardEmojiInput] = new ArgEncoder[ToggleAwardEmojiInput] {

      override def encode(value: ToggleAwardEmojiInput): Value =
        ObjectValue(
          List(
            "awardableId" -> implicitly[ArgEncoder[String]].encode(value.awardableId),
            "name" -> implicitly[ArgEncoder[String]].encode(value.name),
            "clientMutationId" -> value.clientMutationId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value))
          )
        )

      override def typeName: String = "ToggleAwardEmojiInput"
    }

  }

  case class UpdateAlertStatusInput(
    projectPath: String,
    iid: String,
    status: AlertManagementStatus,
    clientMutationId: Option[String] = None
  )

  object UpdateAlertStatusInput {

    implicit val encoder: ArgEncoder[UpdateAlertStatusInput] = new ArgEncoder[UpdateAlertStatusInput] {

      override def encode(value: UpdateAlertStatusInput): Value =
        ObjectValue(
          List(
            "projectPath" -> implicitly[ArgEncoder[String]].encode(value.projectPath),
            "iid" -> implicitly[ArgEncoder[String]].encode(value.iid),
            "status" -> implicitly[ArgEncoder[AlertManagementStatus]].encode(value.status),
            "clientMutationId" -> value.clientMutationId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value))
          )
        )

      override def typeName: String = "UpdateAlertStatusInput"
    }

  }

  case class UpdateContainerExpirationPolicyInput(
    projectPath: String,
    enabled: Option[Boolean] = None,
    cadence: Option[ContainerExpirationPolicyCadenceEnum] = None,
    olderThan: Option[ContainerExpirationPolicyOlderThanEnum] = None,
    keepN: Option[ContainerExpirationPolicyKeepEnum] = None,
    nameRegex: Option[UntrustedRegexp] = None,
    nameRegexKeep: Option[UntrustedRegexp] = None,
    clientMutationId: Option[String] = None
  )

  object UpdateContainerExpirationPolicyInput {

    implicit val encoder: ArgEncoder[UpdateContainerExpirationPolicyInput] = new ArgEncoder[UpdateContainerExpirationPolicyInput] {

      override def encode(value: UpdateContainerExpirationPolicyInput): Value =
        ObjectValue(
          List(
            "projectPath" -> implicitly[ArgEncoder[String]].encode(value.projectPath),
            "enabled" -> value.enabled.fold(NullValue: Value)(value => implicitly[ArgEncoder[Boolean]].encode(value)),
            "cadence" -> value
              .cadence
              .fold(NullValue: Value)(value => implicitly[ArgEncoder[ContainerExpirationPolicyCadenceEnum]].encode(value)),
            "olderThan" -> value
              .olderThan
              .fold(NullValue: Value)(value => implicitly[ArgEncoder[ContainerExpirationPolicyOlderThanEnum]].encode(value)),
            "keepN" -> value.keepN.fold(NullValue: Value)(value => implicitly[ArgEncoder[ContainerExpirationPolicyKeepEnum]].encode(value)),
            "nameRegex" -> value.nameRegex.fold(NullValue: Value)(value => implicitly[ArgEncoder[UntrustedRegexp]].encode(value)),
            "nameRegexKeep" -> value.nameRegexKeep.fold(NullValue: Value)(value => implicitly[ArgEncoder[UntrustedRegexp]].encode(value)),
            "clientMutationId" -> value.clientMutationId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value))
          )
        )

      override def typeName: String = "UpdateContainerExpirationPolicyInput"
    }

  }

  case class UpdateDiffImagePositionInput(
    x: Option[Int] = None,
    y: Option[Int] = None,
    width: Option[Int] = None,
    height: Option[Int] = None
  )

  object UpdateDiffImagePositionInput {

    implicit val encoder: ArgEncoder[UpdateDiffImagePositionInput] = new ArgEncoder[UpdateDiffImagePositionInput] {

      override def encode(value: UpdateDiffImagePositionInput): Value =
        ObjectValue(
          List(
            "x" -> value.x.fold(NullValue: Value)(value => implicitly[ArgEncoder[Int]].encode(value)),
            "y" -> value.y.fold(NullValue: Value)(value => implicitly[ArgEncoder[Int]].encode(value)),
            "width" -> value.width.fold(NullValue: Value)(value => implicitly[ArgEncoder[Int]].encode(value)),
            "height" -> value.height.fold(NullValue: Value)(value => implicitly[ArgEncoder[Int]].encode(value))
          )
        )

      override def typeName: String = "UpdateDiffImagePositionInput"
    }

  }

  case class UpdateEpicInput(
    iid: String,
    groupPath: String,
    title: Option[String] = None,
    description: Option[String] = None,
    confidential: Option[Boolean] = None,
    startDateFixed: Option[String] = None,
    dueDateFixed: Option[String] = None,
    startDateIsFixed: Option[Boolean] = None,
    dueDateIsFixed: Option[Boolean] = None,
    addLabelIds: Option[List[String]] = None,
    removeLabelIds: Option[List[String]] = None,
    stateEvent: Option[EpicStateEvent] = None,
    clientMutationId: Option[String] = None
  )

  object UpdateEpicInput {

    implicit val encoder: ArgEncoder[UpdateEpicInput] = new ArgEncoder[UpdateEpicInput] {

      override def encode(value: UpdateEpicInput): Value =
        ObjectValue(
          List(
            "iid" -> implicitly[ArgEncoder[String]].encode(value.iid),
            "groupPath" -> implicitly[ArgEncoder[String]].encode(value.groupPath),
            "title" -> value.title.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value)),
            "description" -> value.description.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value)),
            "confidential" -> value.confidential.fold(NullValue: Value)(value => implicitly[ArgEncoder[Boolean]].encode(value)),
            "startDateFixed" -> value.startDateFixed.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value)),
            "dueDateFixed" -> value.dueDateFixed.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value)),
            "startDateIsFixed" -> value.startDateIsFixed.fold(NullValue: Value)(value => implicitly[ArgEncoder[Boolean]].encode(value)),
            "dueDateIsFixed" -> value.dueDateIsFixed.fold(NullValue: Value)(value => implicitly[ArgEncoder[Boolean]].encode(value)),
            "addLabelIds" -> value
              .addLabelIds
              .fold(NullValue: Value)(value => ListValue(value.map(value => implicitly[ArgEncoder[String]].encode(value)))),
            "removeLabelIds" -> value
              .removeLabelIds
              .fold(NullValue: Value)(value => ListValue(value.map(value => implicitly[ArgEncoder[String]].encode(value)))),
            "stateEvent" -> value.stateEvent.fold(NullValue: Value)(value => implicitly[ArgEncoder[EpicStateEvent]].encode(value)),
            "clientMutationId" -> value.clientMutationId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value))
          )
        )

      override def typeName: String = "UpdateEpicInput"
    }

  }

  case class UpdateImageDiffNoteInput(
    id: String,
    body: Option[String] = None,
    position: Option[UpdateDiffImagePositionInput] = None,
    clientMutationId: Option[String] = None
  )

  object UpdateImageDiffNoteInput {

    implicit val encoder: ArgEncoder[UpdateImageDiffNoteInput] = new ArgEncoder[UpdateImageDiffNoteInput] {

      override def encode(value: UpdateImageDiffNoteInput): Value =
        ObjectValue(
          List(
            "id" -> implicitly[ArgEncoder[String]].encode(value.id),
            "body" -> value.body.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value)),
            "position" -> value
              .position
              .fold(NullValue: Value)(value => implicitly[ArgEncoder[UpdateDiffImagePositionInput]].encode(value)),
            "clientMutationId" -> value.clientMutationId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value))
          )
        )

      override def typeName: String = "UpdateImageDiffNoteInput"
    }

  }

  case class UpdateIssueInput(
    projectPath: String,
    iid: String,
    title: Option[String] = None,
    description: Option[String] = None,
    dueDate: Option[Time] = None,
    confidential: Option[Boolean] = None,
    healthStatus: Option[HealthStatus] = None,
    clientMutationId: Option[String] = None
  )

  object UpdateIssueInput {

    implicit val encoder: ArgEncoder[UpdateIssueInput] = new ArgEncoder[UpdateIssueInput] {

      override def encode(value: UpdateIssueInput): Value =
        ObjectValue(
          List(
            "projectPath" -> implicitly[ArgEncoder[String]].encode(value.projectPath),
            "iid" -> implicitly[ArgEncoder[String]].encode(value.iid),
            "title" -> value.title.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value)),
            "description" -> value.description.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value)),
            "dueDate" -> value.dueDate.fold(NullValue: Value)(value => implicitly[ArgEncoder[Time]].encode(value)),
            "confidential" -> value.confidential.fold(NullValue: Value)(value => implicitly[ArgEncoder[Boolean]].encode(value)),
            "healthStatus" -> value.healthStatus.fold(NullValue: Value)(value => implicitly[ArgEncoder[HealthStatus]].encode(value)),
            "clientMutationId" -> value.clientMutationId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value))
          )
        )

      override def typeName: String = "UpdateIssueInput"
    }

  }

  case class UpdateIterationInput(
    groupPath: String,
    id: String,
    title: Option[String] = None,
    description: Option[String] = None,
    startDate: Option[String] = None,
    dueDate: Option[String] = None,
    clientMutationId: Option[String] = None
  )

  object UpdateIterationInput {

    implicit val encoder: ArgEncoder[UpdateIterationInput] = new ArgEncoder[UpdateIterationInput] {

      override def encode(value: UpdateIterationInput): Value =
        ObjectValue(
          List(
            "groupPath" -> implicitly[ArgEncoder[String]].encode(value.groupPath),
            "id" -> implicitly[ArgEncoder[String]].encode(value.id),
            "title" -> value.title.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value)),
            "description" -> value.description.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value)),
            "startDate" -> value.startDate.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value)),
            "dueDate" -> value.dueDate.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value)),
            "clientMutationId" -> value.clientMutationId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value))
          )
        )

      override def typeName: String = "UpdateIterationInput"
    }

  }

  case class UpdateNoteInput(id: String, body: String, clientMutationId: Option[String] = None)

  object UpdateNoteInput {

    implicit val encoder: ArgEncoder[UpdateNoteInput] = new ArgEncoder[UpdateNoteInput] {

      override def encode(value: UpdateNoteInput): Value =
        ObjectValue(
          List(
            "id" -> implicitly[ArgEncoder[String]].encode(value.id),
            "body" -> implicitly[ArgEncoder[String]].encode(value.body),
            "clientMutationId" -> value.clientMutationId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value))
          )
        )

      override def typeName: String = "UpdateNoteInput"
    }

  }

  case class UpdateRequirementInput(
    title: Option[String] = None,
    state: Option[RequirementState] = None,
    iid: String,
    projectPath: String,
    clientMutationId: Option[String] = None
  )

  object UpdateRequirementInput {

    implicit val encoder: ArgEncoder[UpdateRequirementInput] = new ArgEncoder[UpdateRequirementInput] {

      override def encode(value: UpdateRequirementInput): Value =
        ObjectValue(
          List(
            "title" -> value.title.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value)),
            "state" -> value.state.fold(NullValue: Value)(value => implicitly[ArgEncoder[RequirementState]].encode(value)),
            "iid" -> implicitly[ArgEncoder[String]].encode(value.iid),
            "projectPath" -> implicitly[ArgEncoder[String]].encode(value.projectPath),
            "clientMutationId" -> value.clientMutationId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value))
          )
        )

      override def typeName: String = "UpdateRequirementInput"
    }

  }

  case class UpdateSnippetInput(
    id: String,
    title: Option[String] = None,
    fileName: Option[String] = None,
    content: Option[String] = None,
    description: Option[String] = None,
    visibilityLevel: Option[VisibilityLevelsEnum] = None,
    blobActions: Option[List[SnippetBlobActionInputType]] = None,
    clientMutationId: Option[String] = None
  )

  object UpdateSnippetInput {

    implicit val encoder: ArgEncoder[UpdateSnippetInput] = new ArgEncoder[UpdateSnippetInput] {

      override def encode(value: UpdateSnippetInput): Value =
        ObjectValue(
          List(
            "id" -> implicitly[ArgEncoder[String]].encode(value.id),
            "title" -> value.title.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value)),
            "fileName" -> value.fileName.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value)),
            "content" -> value.content.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value)),
            "description" -> value.description.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value)),
            "visibilityLevel" -> value
              .visibilityLevel
              .fold(NullValue: Value)(value => implicitly[ArgEncoder[VisibilityLevelsEnum]].encode(value)),
            "blobActions" -> value
              .blobActions
              .fold(NullValue: Value)(value =>
                ListValue(value.map(value => implicitly[ArgEncoder[SnippetBlobActionInputType]].encode(value)))
              ),
            "clientMutationId" -> value.clientMutationId.fold(NullValue: Value)(value => implicitly[ArgEncoder[String]].encode(value))
          )
        )

      override def typeName: String = "UpdateSnippetInput"
    }

  }

  type Query = RootQuery

  object Query {

    /**
      * Get information about current user
      */
    def currentUser[A](innerSelection: SelectionBuilder[User, A]): SelectionBuilder[RootQuery, Option[A]] =
      Field("currentUser", OptionOf(Obj(innerSelection)))

    /**
      * Fields related to design management
      */
    def designManagement[A](innerSelection: SelectionBuilder[DesignManagement, A]): SelectionBuilder[RootQuery, A] =
      Field("designManagement", Obj(innerSelection))

    /**
      * Text to echo back
      */
    def echo(text: String): SelectionBuilder[RootQuery, String] = Field("echo", Scalar(), arguments = List(Argument("text", text)))

    /**
      * Find a Geo node
      */
    def geoNode[A](name: Option[String] = None)(innerSelection: SelectionBuilder[GeoNode, A]): SelectionBuilder[RootQuery, Option[A]] =
      Field("geoNode", OptionOf(Obj(innerSelection)), arguments = List(Argument("name", name)))

    /**
      * Find a group
      */
    def group[A](fullPath: String)(innerSelection: SelectionBuilder[Group, A]): SelectionBuilder[RootQuery, Option[A]] =
      Field("group", OptionOf(Obj(innerSelection)), arguments = List(Argument("fullPath", fullPath)))

    /**
      * Fields related to Instance Security Dashboard
      */
    def instanceSecurityDashboard[A](
      innerSelection: SelectionBuilder[InstanceSecurityDashboard, A]
    ): SelectionBuilder[RootQuery, Option[A]] = Field("instanceSecurityDashboard", OptionOf(Obj(innerSelection)))

    /**
      * Metadata about GitLab
      */
    def metadata[A](innerSelection: SelectionBuilder[Metadata, A]): SelectionBuilder[RootQuery, Option[A]] =
      Field("metadata", OptionOf(Obj(innerSelection)))

    /**
      * Find a namespace
      */
    def namespace[A](fullPath: String)(innerSelection: SelectionBuilder[Namespace, A]): SelectionBuilder[RootQuery, Option[A]] =
      Field("namespace", OptionOf(Obj(innerSelection)), arguments = List(Argument("fullPath", fullPath)))

    /**
      * Find a project
      */
    def project[A](fullPath: String)(innerSelection: SelectionBuilder[Project, A]): SelectionBuilder[RootQuery, Option[A]] =
      Field("project", OptionOf(Obj(innerSelection)), arguments = List(Argument("fullPath", fullPath)))

    /**
      * Find projects visible to the current user
      */
    def projects[A](
      membership: Option[Boolean] = None,
      search: Option[String] = None,
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[ProjectConnection, A]
    ): SelectionBuilder[RootQuery, Option[A]] =
      Field(
        "projects",
        OptionOf(Obj(innerSelection)),
        arguments = List(
          Argument("membership", membership),
          Argument("search", search),
          Argument("after", after),
          Argument("before", before),
          Argument("first", first),
          Argument("last", last)
        )
      )

    /**
      * Find Snippets visible to the current user
      */
    def snippets[A](
      ids: Option[List[String]] = None,
      visibility: Option[VisibilityScopesEnum] = None,
      authorId: Option[String] = None,
      projectId: Option[String] = None,
      `type`: Option[TypeEnum] = None,
      explore: Option[Boolean] = None,
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[SnippetConnection, A]
    ): SelectionBuilder[RootQuery, Option[A]] =
      Field(
        "snippets",
        OptionOf(Obj(innerSelection)),
        arguments = List(
          Argument("ids", ids),
          Argument("visibility", visibility),
          Argument("authorId", authorId),
          Argument("projectId", projectId),
          Argument("type", `type`),
          Argument("explore", explore),
          Argument("after", after),
          Argument("before", before),
          Argument("first", first),
          Argument("last", last)
        )
      )

    /**
      * Find a user
      */
    def user[A](
      id: Option[String] = None,
      username: Option[String] = None
    )(
      innerSelection: SelectionBuilder[User, A]
    ): SelectionBuilder[RootQuery, Option[A]] =
      Field("user", OptionOf(Obj(innerSelection)), arguments = List(Argument("id", id), Argument("username", username)))

    /**
      * Find users
      */
    def users[A](
      ids: Option[List[String]] = None,
      usernames: Option[List[String]] = None,
      sort: Option[Sort] = None,
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[UserConnection, A]
    ): SelectionBuilder[RootQuery, Option[A]] =
      Field(
        "users",
        OptionOf(Obj(innerSelection)),
        arguments = List(
          Argument("ids", ids),
          Argument("usernames", usernames),
          Argument("sort", sort),
          Argument("after", after),
          Argument("before", before),
          Argument("first", first),
          Argument("last", last)
        )
      )

    /**
      * Vulnerabilities reported on projects on the current user's instance security dashboard
      */
    def vulnerabilities[A](
      projectId: Option[List[String]] = None,
      reportType: Option[List[VulnerabilityReportType]] = None,
      severity: Option[List[VulnerabilitySeverity]] = None,
      state: Option[List[VulnerabilityState]] = None,
      scanner: Option[List[String]] = None,
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[VulnerabilityConnection, A]
    ): SelectionBuilder[RootQuery, Option[A]] =
      Field(
        "vulnerabilities",
        OptionOf(Obj(innerSelection)),
        arguments = List(
          Argument("projectId", projectId),
          Argument("reportType", reportType),
          Argument("severity", severity),
          Argument("state", state),
          Argument("scanner", scanner),
          Argument("after", after),
          Argument("before", before),
          Argument("first", first),
          Argument("last", last)
        )
      )

    /**
      * Number of vulnerabilities per severity level, per day, for the projects on the current user's instance security dashboard
      */
    def vulnerabilitiesCountByDayAndSeverity[A](
      startDate: ISO8601Date,
      endDate: ISO8601Date,
      after: Option[String] = None,
      before: Option[String] = None,
      first: Option[Int] = None,
      last: Option[Int] = None
    )(
      innerSelection: SelectionBuilder[VulnerabilitiesCountByDayAndSeverityConnection, A]
    ): SelectionBuilder[RootQuery, Option[A]] =
      Field(
        "vulnerabilitiesCountByDayAndSeverity",
        OptionOf(Obj(innerSelection)),
        arguments = List(
          Argument("startDate", startDate),
          Argument("endDate", endDate),
          Argument("after", after),
          Argument("before", before),
          Argument("first", first),
          Argument("last", last)
        )
      )

  }

  type Mutation = RootMutation

  object Mutation {

    @deprecated("Use awardEmojiAdd. Deprecated in 13.2", "")
    def addAwardEmoji[A](
      input: AddAwardEmojiInput
    )(
      innerSelection: SelectionBuilder[AddAwardEmojiPayload, A]
    ): SelectionBuilder[RootMutation, Option[A]] =
      Field("addAwardEmoji", OptionOf(Obj(innerSelection)), arguments = List(Argument("input", input)))

    def addProjectToSecurityDashboard[A](
      input: AddProjectToSecurityDashboardInput
    )(
      innerSelection: SelectionBuilder[AddProjectToSecurityDashboardPayload, A]
    ): SelectionBuilder[RootMutation, Option[A]] =
      Field("addProjectToSecurityDashboard", OptionOf(Obj(innerSelection)), arguments = List(Argument("input", input)))

    def adminSidekiqQueuesDeleteJobs[A](
      input: AdminSidekiqQueuesDeleteJobsInput
    )(
      innerSelection: SelectionBuilder[AdminSidekiqQueuesDeleteJobsPayload, A]
    ): SelectionBuilder[RootMutation, Option[A]] =
      Field("adminSidekiqQueuesDeleteJobs", OptionOf(Obj(innerSelection)), arguments = List(Argument("input", input)))

    def alertSetAssignees[A](
      input: AlertSetAssigneesInput
    )(
      innerSelection: SelectionBuilder[AlertSetAssigneesPayload, A]
    ): SelectionBuilder[RootMutation, Option[A]] =
      Field("alertSetAssignees", OptionOf(Obj(innerSelection)), arguments = List(Argument("input", input)))

    def alertTodoCreate[A](
      input: AlertTodoCreateInput
    )(
      innerSelection: SelectionBuilder[AlertTodoCreatePayload, A]
    ): SelectionBuilder[RootMutation, Option[A]] =
      Field("alertTodoCreate", OptionOf(Obj(innerSelection)), arguments = List(Argument("input", input)))

    def awardEmojiAdd[A](
      input: AwardEmojiAddInput
    )(
      innerSelection: SelectionBuilder[AwardEmojiAddPayload, A]
    ): SelectionBuilder[RootMutation, Option[A]] =
      Field("awardEmojiAdd", OptionOf(Obj(innerSelection)), arguments = List(Argument("input", input)))

    def awardEmojiRemove[A](
      input: AwardEmojiRemoveInput
    )(
      innerSelection: SelectionBuilder[AwardEmojiRemovePayload, A]
    ): SelectionBuilder[RootMutation, Option[A]] =
      Field("awardEmojiRemove", OptionOf(Obj(innerSelection)), arguments = List(Argument("input", input)))

    def awardEmojiToggle[A](
      input: AwardEmojiToggleInput
    )(
      innerSelection: SelectionBuilder[AwardEmojiTogglePayload, A]
    ): SelectionBuilder[RootMutation, Option[A]] =
      Field("awardEmojiToggle", OptionOf(Obj(innerSelection)), arguments = List(Argument("input", input)))

    def boardListUpdateLimitMetrics[A](
      input: BoardListUpdateLimitMetricsInput
    )(
      innerSelection: SelectionBuilder[BoardListUpdateLimitMetricsPayload, A]
    ): SelectionBuilder[RootMutation, Option[A]] =
      Field("boardListUpdateLimitMetrics", OptionOf(Obj(innerSelection)), arguments = List(Argument("input", input)))

    def commitCreate[A](
      input: CommitCreateInput
    )(
      innerSelection: SelectionBuilder[CommitCreatePayload, A]
    ): SelectionBuilder[RootMutation, Option[A]] =
      Field("commitCreate", OptionOf(Obj(innerSelection)), arguments = List(Argument("input", input)))

    def createAlertIssue[A](
      input: CreateAlertIssueInput
    )(
      innerSelection: SelectionBuilder[CreateAlertIssuePayload, A]
    ): SelectionBuilder[RootMutation, Option[A]] =
      Field("createAlertIssue", OptionOf(Obj(innerSelection)), arguments = List(Argument("input", input)))

    def createAnnotation[A](
      input: CreateAnnotationInput
    )(
      innerSelection: SelectionBuilder[CreateAnnotationPayload, A]
    ): SelectionBuilder[RootMutation, Option[A]] =
      Field("createAnnotation", OptionOf(Obj(innerSelection)), arguments = List(Argument("input", input)))

    def createBranch[A](
      input: CreateBranchInput
    )(
      innerSelection: SelectionBuilder[CreateBranchPayload, A]
    ): SelectionBuilder[RootMutation, Option[A]] =
      Field("createBranch", OptionOf(Obj(innerSelection)), arguments = List(Argument("input", input)))

    def createDiffNote[A](
      input: CreateDiffNoteInput
    )(
      innerSelection: SelectionBuilder[CreateDiffNotePayload, A]
    ): SelectionBuilder[RootMutation, Option[A]] =
      Field("createDiffNote", OptionOf(Obj(innerSelection)), arguments = List(Argument("input", input)))

    def createEpic[A](
      input: CreateEpicInput
    )(
      innerSelection: SelectionBuilder[CreateEpicPayload, A]
    ): SelectionBuilder[RootMutation, Option[A]] =
      Field("createEpic", OptionOf(Obj(innerSelection)), arguments = List(Argument("input", input)))

    def createImageDiffNote[A](
      input: CreateImageDiffNoteInput
    )(
      innerSelection: SelectionBuilder[CreateImageDiffNotePayload, A]
    ): SelectionBuilder[RootMutation, Option[A]] =
      Field("createImageDiffNote", OptionOf(Obj(innerSelection)), arguments = List(Argument("input", input)))

    def createIteration[A](
      input: CreateIterationInput
    )(
      innerSelection: SelectionBuilder[CreateIterationPayload, A]
    ): SelectionBuilder[RootMutation, Option[A]] =
      Field("createIteration", OptionOf(Obj(innerSelection)), arguments = List(Argument("input", input)))

    def createNote[A](
      input: CreateNoteInput
    )(
      innerSelection: SelectionBuilder[CreateNotePayload, A]
    ): SelectionBuilder[RootMutation, Option[A]] =
      Field("createNote", OptionOf(Obj(innerSelection)), arguments = List(Argument("input", input)))

    def createRequirement[A](
      input: CreateRequirementInput
    )(
      innerSelection: SelectionBuilder[CreateRequirementPayload, A]
    ): SelectionBuilder[RootMutation, Option[A]] =
      Field("createRequirement", OptionOf(Obj(innerSelection)), arguments = List(Argument("input", input)))

    def createSnippet[A](
      input: CreateSnippetInput
    )(
      innerSelection: SelectionBuilder[CreateSnippetPayload, A]
    ): SelectionBuilder[RootMutation, Option[A]] =
      Field("createSnippet", OptionOf(Obj(innerSelection)), arguments = List(Argument("input", input)))

    def dastSiteProfileCreate[A](
      input: DastSiteProfileCreateInput
    )(
      innerSelection: SelectionBuilder[DastSiteProfileCreatePayload, A]
    ): SelectionBuilder[RootMutation, Option[A]] =
      Field("dastSiteProfileCreate", OptionOf(Obj(innerSelection)), arguments = List(Argument("input", input)))

    def deleteAnnotation[A](
      input: DeleteAnnotationInput
    )(
      innerSelection: SelectionBuilder[DeleteAnnotationPayload, A]
    ): SelectionBuilder[RootMutation, Option[A]] =
      Field("deleteAnnotation", OptionOf(Obj(innerSelection)), arguments = List(Argument("input", input)))

    def designManagementDelete[A](
      input: DesignManagementDeleteInput
    )(
      innerSelection: SelectionBuilder[DesignManagementDeletePayload, A]
    ): SelectionBuilder[RootMutation, Option[A]] =
      Field("designManagementDelete", OptionOf(Obj(innerSelection)), arguments = List(Argument("input", input)))

    def designManagementUpload[A](
      input: DesignManagementUploadInput
    )(
      innerSelection: SelectionBuilder[DesignManagementUploadPayload, A]
    ): SelectionBuilder[RootMutation, Option[A]] =
      Field("designManagementUpload", OptionOf(Obj(innerSelection)), arguments = List(Argument("input", input)))

    def destroyNote[A](
      input: DestroyNoteInput
    )(
      innerSelection: SelectionBuilder[DestroyNotePayload, A]
    ): SelectionBuilder[RootMutation, Option[A]] =
      Field("destroyNote", OptionOf(Obj(innerSelection)), arguments = List(Argument("input", input)))

    def destroySnippet[A](
      input: DestroySnippetInput
    )(
      innerSelection: SelectionBuilder[DestroySnippetPayload, A]
    ): SelectionBuilder[RootMutation, Option[A]] =
      Field("destroySnippet", OptionOf(Obj(innerSelection)), arguments = List(Argument("input", input)))

    /**
      * Toggles the resolved state of a discussion
      */
    def discussionToggleResolve[A](
      input: DiscussionToggleResolveInput
    )(
      innerSelection: SelectionBuilder[DiscussionToggleResolvePayload, A]
    ): SelectionBuilder[RootMutation, Option[A]] =
      Field("discussionToggleResolve", OptionOf(Obj(innerSelection)), arguments = List(Argument("input", input)))

    def dismissVulnerability[A](
      input: DismissVulnerabilityInput
    )(
      innerSelection: SelectionBuilder[DismissVulnerabilityPayload, A]
    ): SelectionBuilder[RootMutation, Option[A]] =
      Field("dismissVulnerability", OptionOf(Obj(innerSelection)), arguments = List(Argument("input", input)))

    def epicAddIssue[A](
      input: EpicAddIssueInput
    )(
      innerSelection: SelectionBuilder[EpicAddIssuePayload, A]
    ): SelectionBuilder[RootMutation, Option[A]] =
      Field("epicAddIssue", OptionOf(Obj(innerSelection)), arguments = List(Argument("input", input)))

    def epicSetSubscription[A](
      input: EpicSetSubscriptionInput
    )(
      innerSelection: SelectionBuilder[EpicSetSubscriptionPayload, A]
    ): SelectionBuilder[RootMutation, Option[A]] =
      Field("epicSetSubscription", OptionOf(Obj(innerSelection)), arguments = List(Argument("input", input)))

    def epicTreeReorder[A](
      input: EpicTreeReorderInput
    )(
      innerSelection: SelectionBuilder[EpicTreeReorderPayload, A]
    ): SelectionBuilder[RootMutation, Option[A]] =
      Field("epicTreeReorder", OptionOf(Obj(innerSelection)), arguments = List(Argument("input", input)))

    def issueSetConfidential[A](
      input: IssueSetConfidentialInput
    )(
      innerSelection: SelectionBuilder[IssueSetConfidentialPayload, A]
    ): SelectionBuilder[RootMutation, Option[A]] =
      Field("issueSetConfidential", OptionOf(Obj(innerSelection)), arguments = List(Argument("input", input)))

    def issueSetDueDate[A](
      input: IssueSetDueDateInput
    )(
      innerSelection: SelectionBuilder[IssueSetDueDatePayload, A]
    ): SelectionBuilder[RootMutation, Option[A]] =
      Field("issueSetDueDate", OptionOf(Obj(innerSelection)), arguments = List(Argument("input", input)))

    def issueSetIteration[A](
      input: IssueSetIterationInput
    )(
      innerSelection: SelectionBuilder[IssueSetIterationPayload, A]
    ): SelectionBuilder[RootMutation, Option[A]] =
      Field("issueSetIteration", OptionOf(Obj(innerSelection)), arguments = List(Argument("input", input)))

    def issueSetLocked[A](
      input: IssueSetLockedInput
    )(
      innerSelection: SelectionBuilder[IssueSetLockedPayload, A]
    ): SelectionBuilder[RootMutation, Option[A]] =
      Field("issueSetLocked", OptionOf(Obj(innerSelection)), arguments = List(Argument("input", input)))

    def issueSetWeight[A](
      input: IssueSetWeightInput
    )(
      innerSelection: SelectionBuilder[IssueSetWeightPayload, A]
    ): SelectionBuilder[RootMutation, Option[A]] =
      Field("issueSetWeight", OptionOf(Obj(innerSelection)), arguments = List(Argument("input", input)))

    def jiraImportStart[A](
      input: JiraImportStartInput
    )(
      innerSelection: SelectionBuilder[JiraImportStartPayload, A]
    ): SelectionBuilder[RootMutation, Option[A]] =
      Field("jiraImportStart", OptionOf(Obj(innerSelection)), arguments = List(Argument("input", input)))

    def jiraImportUsers[A](
      input: JiraImportUsersInput
    )(
      innerSelection: SelectionBuilder[JiraImportUsersPayload, A]
    ): SelectionBuilder[RootMutation, Option[A]] =
      Field("jiraImportUsers", OptionOf(Obj(innerSelection)), arguments = List(Argument("input", input)))

    def markAsSpamSnippet[A](
      input: MarkAsSpamSnippetInput
    )(
      innerSelection: SelectionBuilder[MarkAsSpamSnippetPayload, A]
    ): SelectionBuilder[RootMutation, Option[A]] =
      Field("markAsSpamSnippet", OptionOf(Obj(innerSelection)), arguments = List(Argument("input", input)))

    def mergeRequestCreate[A](
      input: MergeRequestCreateInput
    )(
      innerSelection: SelectionBuilder[MergeRequestCreatePayload, A]
    ): SelectionBuilder[RootMutation, Option[A]] =
      Field("mergeRequestCreate", OptionOf(Obj(innerSelection)), arguments = List(Argument("input", input)))

    def mergeRequestSetAssignees[A](
      input: MergeRequestSetAssigneesInput
    )(
      innerSelection: SelectionBuilder[MergeRequestSetAssigneesPayload, A]
    ): SelectionBuilder[RootMutation, Option[A]] =
      Field("mergeRequestSetAssignees", OptionOf(Obj(innerSelection)), arguments = List(Argument("input", input)))

    def mergeRequestSetLabels[A](
      input: MergeRequestSetLabelsInput
    )(
      innerSelection: SelectionBuilder[MergeRequestSetLabelsPayload, A]
    ): SelectionBuilder[RootMutation, Option[A]] =
      Field("mergeRequestSetLabels", OptionOf(Obj(innerSelection)), arguments = List(Argument("input", input)))

    def mergeRequestSetLocked[A](
      input: MergeRequestSetLockedInput
    )(
      innerSelection: SelectionBuilder[MergeRequestSetLockedPayload, A]
    ): SelectionBuilder[RootMutation, Option[A]] =
      Field("mergeRequestSetLocked", OptionOf(Obj(innerSelection)), arguments = List(Argument("input", input)))

    def mergeRequestSetMilestone[A](
      input: MergeRequestSetMilestoneInput
    )(
      innerSelection: SelectionBuilder[MergeRequestSetMilestonePayload, A]
    ): SelectionBuilder[RootMutation, Option[A]] =
      Field("mergeRequestSetMilestone", OptionOf(Obj(innerSelection)), arguments = List(Argument("input", input)))

    def mergeRequestSetSubscription[A](
      input: MergeRequestSetSubscriptionInput
    )(
      innerSelection: SelectionBuilder[MergeRequestSetSubscriptionPayload, A]
    ): SelectionBuilder[RootMutation, Option[A]] =
      Field("mergeRequestSetSubscription", OptionOf(Obj(innerSelection)), arguments = List(Argument("input", input)))

    def mergeRequestSetWip[A](
      input: MergeRequestSetWipInput
    )(
      innerSelection: SelectionBuilder[MergeRequestSetWipPayload, A]
    ): SelectionBuilder[RootMutation, Option[A]] =
      Field("mergeRequestSetWip", OptionOf(Obj(innerSelection)), arguments = List(Argument("input", input)))

    /**
      * Update attributes of a merge request
      */
    def mergeRequestUpdate[A](
      input: MergeRequestUpdateInput
    )(
      innerSelection: SelectionBuilder[MergeRequestUpdatePayload, A]
    ): SelectionBuilder[RootMutation, Option[A]] =
      Field("mergeRequestUpdate", OptionOf(Obj(innerSelection)), arguments = List(Argument("input", input)))

    @deprecated("Use awardEmojiRemove. Deprecated in 13.2", "")
    def removeAwardEmoji[A](
      input: RemoveAwardEmojiInput
    )(
      innerSelection: SelectionBuilder[RemoveAwardEmojiPayload, A]
    ): SelectionBuilder[RootMutation, Option[A]] =
      Field("removeAwardEmoji", OptionOf(Obj(innerSelection)), arguments = List(Argument("input", input)))

    def removeProjectFromSecurityDashboard[A](
      input: RemoveProjectFromSecurityDashboardInput
    )(
      innerSelection: SelectionBuilder[RemoveProjectFromSecurityDashboardPayload, A]
    ): SelectionBuilder[RootMutation, Option[A]] =
      Field("removeProjectFromSecurityDashboard", OptionOf(Obj(innerSelection)), arguments = List(Argument("input", input)))

    def runDastScan[A](
      input: RunDASTScanInput
    )(
      innerSelection: SelectionBuilder[RunDASTScanPayload, A]
    ): SelectionBuilder[RootMutation, Option[A]] =
      Field("runDastScan", OptionOf(Obj(innerSelection)), arguments = List(Argument("input", input)))

    def todoMarkDone[A](
      input: TodoMarkDoneInput
    )(
      innerSelection: SelectionBuilder[TodoMarkDonePayload, A]
    ): SelectionBuilder[RootMutation, Option[A]] =
      Field("todoMarkDone", OptionOf(Obj(innerSelection)), arguments = List(Argument("input", input)))

    def todoRestore[A](
      input: TodoRestoreInput
    )(
      innerSelection: SelectionBuilder[TodoRestorePayload, A]
    ): SelectionBuilder[RootMutation, Option[A]] =
      Field("todoRestore", OptionOf(Obj(innerSelection)), arguments = List(Argument("input", input)))

    def todoRestoreMany[A](
      input: TodoRestoreManyInput
    )(
      innerSelection: SelectionBuilder[TodoRestoreManyPayload, A]
    ): SelectionBuilder[RootMutation, Option[A]] =
      Field("todoRestoreMany", OptionOf(Obj(innerSelection)), arguments = List(Argument("input", input)))

    def todosMarkAllDone[A](
      input: TodosMarkAllDoneInput
    )(
      innerSelection: SelectionBuilder[TodosMarkAllDonePayload, A]
    ): SelectionBuilder[RootMutation, Option[A]] =
      Field("todosMarkAllDone", OptionOf(Obj(innerSelection)), arguments = List(Argument("input", input)))

    @deprecated("Use awardEmojiToggle. Deprecated in 13.2", "")
    def toggleAwardEmoji[A](
      input: ToggleAwardEmojiInput
    )(
      innerSelection: SelectionBuilder[ToggleAwardEmojiPayload, A]
    ): SelectionBuilder[RootMutation, Option[A]] =
      Field("toggleAwardEmoji", OptionOf(Obj(innerSelection)), arguments = List(Argument("input", input)))

    def updateAlertStatus[A](
      input: UpdateAlertStatusInput
    )(
      innerSelection: SelectionBuilder[UpdateAlertStatusPayload, A]
    ): SelectionBuilder[RootMutation, Option[A]] =
      Field("updateAlertStatus", OptionOf(Obj(innerSelection)), arguments = List(Argument("input", input)))

    def updateContainerExpirationPolicy[A](
      input: UpdateContainerExpirationPolicyInput
    )(
      innerSelection: SelectionBuilder[UpdateContainerExpirationPolicyPayload, A]
    ): SelectionBuilder[RootMutation, Option[A]] =
      Field("updateContainerExpirationPolicy", OptionOf(Obj(innerSelection)), arguments = List(Argument("input", input)))

    def updateEpic[A](
      input: UpdateEpicInput
    )(
      innerSelection: SelectionBuilder[UpdateEpicPayload, A]
    ): SelectionBuilder[RootMutation, Option[A]] =
      Field("updateEpic", OptionOf(Obj(innerSelection)), arguments = List(Argument("input", input)))

    /**
      * Updates a DiffNote on an image (a `Note` where the `position.positionType` is `"image"`). If the body of the Note contains only quick actions, the Note will be destroyed during the update, and no Note will be returned
      */
    def updateImageDiffNote[A](
      input: UpdateImageDiffNoteInput
    )(
      innerSelection: SelectionBuilder[UpdateImageDiffNotePayload, A]
    ): SelectionBuilder[RootMutation, Option[A]] =
      Field("updateImageDiffNote", OptionOf(Obj(innerSelection)), arguments = List(Argument("input", input)))

    def updateIssue[A](
      input: UpdateIssueInput
    )(
      innerSelection: SelectionBuilder[UpdateIssuePayload, A]
    ): SelectionBuilder[RootMutation, Option[A]] =
      Field("updateIssue", OptionOf(Obj(innerSelection)), arguments = List(Argument("input", input)))

    def updateIteration[A](
      input: UpdateIterationInput
    )(
      innerSelection: SelectionBuilder[UpdateIterationPayload, A]
    ): SelectionBuilder[RootMutation, Option[A]] =
      Field("updateIteration", OptionOf(Obj(innerSelection)), arguments = List(Argument("input", input)))

    /**
      * Updates a Note. If the body of the Note contains only quick actions, the Note will be destroyed during the update, and no Note will be returned
      */
    def updateNote[A](
      input: UpdateNoteInput
    )(
      innerSelection: SelectionBuilder[UpdateNotePayload, A]
    ): SelectionBuilder[RootMutation, Option[A]] =
      Field("updateNote", OptionOf(Obj(innerSelection)), arguments = List(Argument("input", input)))

    def updateRequirement[A](
      input: UpdateRequirementInput
    )(
      innerSelection: SelectionBuilder[UpdateRequirementPayload, A]
    ): SelectionBuilder[RootMutation, Option[A]] =
      Field("updateRequirement", OptionOf(Obj(innerSelection)), arguments = List(Argument("input", input)))

    def updateSnippet[A](
      input: UpdateSnippetInput
    )(
      innerSelection: SelectionBuilder[UpdateSnippetPayload, A]
    ): SelectionBuilder[RootMutation, Option[A]] =
      Field("updateSnippet", OptionOf(Obj(innerSelection)), arguments = List(Argument("input", input)))

  }

}

