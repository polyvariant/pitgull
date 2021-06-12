# pitgull

[![License](http://img.shields.io/:license-Apache%202-green.svg)](http://www.apache.org/licenses/LICENSE-2.0.txt)
![Continuous Integration](https://github.com/pitgull/pitgull/workflows/Continuous%20Integration/badge.svg)
[![Powered by cats](https://img.shields.io/badge/powered%20by-cats-blue.svg)](https://github.com/typelevel/cats)
![Gluten free](https://img.shields.io/badge/gluten-free-orange.svg)

## Integrating with Pitgull

Along with Pitgull, we provide a `pitgull-bootstrap` command line utility. This program prepares your GitLab project for integration with Pitgull by deleting existing Scala Steward merge requests and setting up a webhook for triggering Pitgull.
```
CLI Arguments:
 --url - your gitlab url like https://gitlab.com/
 --token - your gitlab personal token, needs to have full access to project
 --project - project ID, can be found on project main page
 --bot - user name of Scala Steward bot user
 --webhook - Pitgull target url like https://pitgull.example.com/webhook
```
### Why delete existing merge requests?

Pitgull will only take action when it's triggered by a webhook. By deleting merge requests we make sure no Scala Steward MR gets unnoticed. If we'd only close them, Scala Steward wouldn't update them, so no webhook would be triggerd.

Additionally, if you have some legacy merge requests for single library, this program makes sure to clean them up. When Scala Steward notices that some dependency is out of date and MR is missing - it will recreate it, so no worries about skipping any updates.

## Development

### Useful commands/links

- https://gitlab.com/-/graphql-explorer - Gitlab API's GraphiQL
- `cat example.dhall | dhall-to-json` - normalize example and convert to JSON
- `http post :8080/webhook @path-to-file.json` - send fake webhook event from file

### Related projects

We're using https://github.com/kubukoz/caliban-gitlab/ for some communication with Gitlab,
as well as https://github.com/softwaremill/tapir + https://github.com/softwaremill/sttp for the actions not available via the GraphQL API.

### Docker

You're going to need docker and docker-compose (or podman/podman-compose, although it hasn't been confirmed to work here yet).

You can use the setup in the `docker` directory to run Scala Steward with [the test repository](https://gitlab.com/kubukoz/demo), or customize it to your needs.
Checkout https://github.com/scala-steward-org/scala-steward/blob/master/docs/running.md#running-scala-steward for more information.
You'll need to add a `pass.sh` file that prints your GitLab token to standard output when run (consult the Scala Steward docs to see how).

After you're all set-up, run `docker-compose up` inside the `docker` directory (or `docker-compose -f docker/docker-compose.yml up` in the project directory).

## Releasing

Docker images are being pushed on every push to `main` and tags starting with `v`.
