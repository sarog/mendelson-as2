# mendelson-as2
This is an unofficial Mendelson AS2 Community Server mirror that tracks the [official upstream SourceForge files](https://sourceforge.net/projects/mec-as2/files/).

The project can be built using maven. A few formatting fixes and minor patches have been applied to the source, but overall the goal of this repo is to welcome additional improvements to the project.

Project Roadmap:
- Building of WAR artifacts
- Jetty bundling / deployment scripts
- Update dependencies to modern versions
- Find and fix security issues (there's a few severe ones!)
- Applying best practices when it comes to Java programming
- Formatting and typo fixes
- Add some crucial documentation that's sorely missing in the community version
- Create a Windows service wrapper
- External database support (e.g. MariaDB)

Building notes
- Need to download [Oracle Help for Java libraries](https://www.oracle.com/tools/downloads/jdeveloper-12c-downloads.html) from OTN (login required) which are part of the JDeveloper "Generic" ~~86MB~~ 180MB package **OR** extract them from the current Mendelson AS2 CE distribution and place them under `lib\help`.
