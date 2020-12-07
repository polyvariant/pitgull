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
