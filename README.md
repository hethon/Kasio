# Kasio

[![Build Status](https://img.shields.io/github/actions/workflow/status/hethon/Kasio/ci.yaml?label=build&logo=github)](https://github.com/hethon/Kasio/actions)
[![Latest Release](https://img.shields.io/github/v/release/hethon/Kasio?label=version&color=blue)](https://github.com/hethon/Kasio/releases)
[![pre-commit](https://img.shields.io/badge/pre--commit-enabled-brightgreen?logo=pre-commit)](https://github.com/pre-commit/pre-commit)



Kasio is a Java Swing calculator app built for a university assignment, featuring a retro design inspired by classic scientific calculators.

![Screenshot](screenshots/image.png)

Swing wasn’t a deliberate tech choice, it was required by the course, but I used the project to go beyond that constraint and explore better software practices. The true focus of this repository is learning tooling and architecture.

During the development process, I learned and implemented:
* **Gradle:** Migrated the project to a standard build system instead of relying on IDE tooling.
* **MVC Architecture:** Completely refactored the codebase to decouple the UI components from the main application logic and eliminate circular dependencies.

I am continuing to use this project as a hands-on way to learn new software engineering concepts. Upcoming goals include:
- [x] Adding unit tests (JUnit).
- [x] Setting up CI/CD pipelines.
- [x] Tag-triggered release workflow.
- [ ] Automated semantic versioning.

### How to Run
You don't need Gradle installed globally. Just use the included wrapper:

```bash
# Mac/Linux
./gradlew run

# Windows
gradlew run
```
