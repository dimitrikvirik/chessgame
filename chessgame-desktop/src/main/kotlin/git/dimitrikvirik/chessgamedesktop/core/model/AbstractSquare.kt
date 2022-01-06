package git.dimitrikvirik.chessgamedesktop.core.model

abstract class AbstractSquare(coordination: Coordination,  prefix: String) :
    GameObject(coordination, "S$prefix") {

    val changeable: Boolean = false


}