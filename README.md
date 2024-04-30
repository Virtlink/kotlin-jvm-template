# Kotlin JVM Template
[![Build][github-build-badge]][github-build]
[![License][license-badge]][license]
[![Maven Release][maven-release-badge]][maven-release]
[![GitHub Release][github-release-badge]][github-release]

This is an opinionated template for Kotlin JVM applications, using the latest Gradle and Kotlin.

This repository contains a root project `mylib-project`, a subproject `mylib`, and Github workflows for building and publishing the project as a package on Github Packages and on Maven Central.

To make this application your own, find-and-replace the following terms:
- `Virtlink` -> your GitHub username or organization
- `com.example` -> the project's Maven group
- `mylib` -> the project's name
Also, replace the license at the bottom of this file and in the `LICENSE` file.


## Quick Start
To build the project:

```shell
./gradlew build
```



## License
[![License: CC0-1.0](https://licensebuttons.net/p/zero/1.0/88x31.png)](http://creativecommons.org/publicdomain/zero/1.0/)
To the extent possible under law, Daniel A. A. Pelsmaeker has waived all copyright and related or neighboring rights to the Kotlin JVM Template repository. Feel free to use this as you see fit, no attribution required.


[github-build-badge]: https://github.com/Virtlink/mylib/actions/workflows/build.yml/badge.svg
[github-build]: https://github.com/Virtlink/mylib/actions
[license-badge]: https://img.shields.io/github/license/Virtlink/mylib
[license]: https://github.com/Virtlink/mylib/blob/main/LICENSE
[maven-release-badge]: https://img.shields.io/maven-central/v/com.example/mylib
[maven-release]: https://mvnrepository.com/artifact/com.example/mylib
[github-release-badge]: https://img.shields.io/github/v/release/Virtlink/mylib
[github-release]: https://github.com/Virtlink/mylib/releases