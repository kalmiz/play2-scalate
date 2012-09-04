package com.gawker.play.plugins

import play.api._
import play.api.templates._
import play.api.Configuration._
import play.api.Play.current

import org.fusesource.scalate._
import org.fusesource.scalate.util._
import org.fusesource.scalate.layout.DefaultLayoutStrategy

class ScalatePlugin(app: Application) extends Plugin {

	lazy val engine = new ScalateEngine

	override def onStart() {
		Logger("scalateplugin").info("start on mode: " + app.mode)
		engine
	}

	def api = engine

	override lazy val enabled = {
	    !app.configuration.getString("scalateplugin").filter(_ == "disabled").isDefined
	}
}

class ScalateEngine {

	var format = Play.configuration.getString("scalate.format") match {
		case Some(configuredFormat) => configuredFormat
		case _ => "mustache"
	}

	val engine = new TemplateEngine
	
	engine.resourceLoader = new FileResourceLoader(Some(Play.getFile("app/views")))
    engine.layoutStrategy = new DefaultLayoutStrategy(engine, "app/views/layouts/default." + format)
    engine.classpath = "tmp/classes"
    engine.workingDirectory = Play.getFile("tmp")
    engine.combinedClassPath = true
    engine.classLoader = Play.classloader

	def render(template: String, attributes: Map[String, Any]): Html = {
		Html(engine.layout(template, attributes))
	}
}

// public inteface
object Scalate {

	private def plugin = play.api.Play.maybeApplication.map{app =>
		app.plugin[ScalatePlugin].getOrElse(throw new RuntimeException("you should enable ScalatePlugin in play.plugins"))
	}.getOrElse(throw new RuntimeException("you should have a running app in scope a this point"))

	def render(template: String, attributes: Map[String, Any]): Html = plugin.api.render(template, attributes)
}
