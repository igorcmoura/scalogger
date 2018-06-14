
lazy val commonSettings = Seq(
  name := "Scalogger",
  version := "0.1",
  scalaVersion := "2.12.5"
)

lazy val objectOriented = (project in file("objectoriented"))
  .settings(commonSettings)

lazy val functional = (project in file("functional"))
  .settings(
    commonSettings,
    libraryDependencies += "org.scalafx" %% "scalafx" % "8.0.144-R12"
  )
