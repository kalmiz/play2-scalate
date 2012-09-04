import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "play2-scalate"
    val appVersion      = "0.1-SNAPSHOT"

	val appDependencies = Seq(
		// Add your project dependencies here,
		//"org.fusesource.scalate" % "scalate-core" % "1.6.0-SNAPSHOT"
		"org.fusesource.scalate" % "scalate-core" % "1.5.3"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
     	// Add your own project settings here      
		organization := "com.gawker",
		resolvers += "Local Maven Repository" at "file://"+Path.userHome+"/.m2/repository"
    )


}
