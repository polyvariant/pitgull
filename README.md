# pitgull

[![License](http://img.shields.io/:license-Apache%202-green.svg)](http://www.apache.org/licenses/LICENSE-2.0.txt)
![Continuous Integration](https://github.com/pitgull/pitgull/workflows/Continuous%20Integration/badge.svg)
[![Powered by cats](https://img.shields.io/badge/powered%20by-cats-blue.svg)](https://github.com/typelevel/cats)
![Gluten free](https://img.shields.io/badge/gluten-free-orange.svg)

## Development

### Useful commands/links

- https://gitlab.com/-/graphql-explorer - Gitlab API's GraphiQL
- `cat example.dhall | dhall-to-json` - normalize example and convert to JSON
- `http post :8080/webhook @path-to-file.json` - send fake webhook event from file

### Related projects

We're using https://github.com/kubukoz/caliban-gitlab/ for some communication with Gitlab,
as well as https://github.com/softwaremill/tapir for the actions not available via the GraphQL API.

### Docker

You can use the setup in the `docker` directory to run Scala Steward with [the test repository](https://gitlab.com/kubukoz/demo), or customize it to your needs.
Checkout https://github.com/scala-steward-org/scala-steward/blob/master/docs/running.md#running-scala-steward for more information.
You'll need to add a `pass.sh` file that prints your GitLab token to standard output when run (consult the Scala Steward docs to see how).

After you're all set-up, run `docker-compose up` inside the `docker` directory (or `docker-compose -f docker/docker-compose.yml up` in the project directory).
