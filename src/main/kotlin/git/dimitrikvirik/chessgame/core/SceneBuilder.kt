package git.dimitrikvirik.chessgame.core

import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.util.Callback
import java.io.File
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL

object SceneBuilder {
    @Throws(MalformedURLException::class)
    fun build(file: File) {
        val resource = file.toURI().toURL()
        val fxmlLoader = loadSynchronously(resource)
        val controller = fxmlLoader.getController<Controller>()
        val parent = fxmlLoader.getRoot<Parent>()
        val sceneContextHolder = BeanContext.getBean(
            SceneContextHolder::class.java
        )
        val str = file.name
        val fileName = str.substring(0, str.lastIndexOf("."))
        val scene = sceneContextHolder.createScene(fileName, parent)
        controller.sceneContextObj = scene
    }



    @Throws(IllegalStateException::class)
    private fun loadSynchronously(resource: URL): FXMLLoader {
        val loader = FXMLLoader(resource)
        loader.controllerFactory = Callback { createControllerForType(it) }
        try {
            loader.load<Any>()
        } catch (ex: IOException) {
            throw IllegalStateException("Cannot load Resource!", ex)
        }
        return loader
    }
    private fun createControllerForType(type: Class<*>): Any {
        return BeanContext.getBean(type)
    }
}
