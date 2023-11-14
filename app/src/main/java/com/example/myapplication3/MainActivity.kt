package com.example.myapplication3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.ar.core.Config
import io.github.sceneview.ar.ArSceneView
import io.github.sceneview.ar.node.ArModelNode
import io.github.sceneview.ar.node.PlacementMode
import io.github.sceneview.math.Position

class MainActivity : AppCompatActivity(){
    lateinit var arView: ArSceneView
    lateinit var placeButton: ExtendedFloatingActionButton
    lateinit var modelNode: ArModelNode

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        arView = findViewById<ArSceneView>(R.id.arView).apply {
            this.lightEstimationMode = Config.LightEstimationMode.DISABLED
        }


        placeButton =findViewById(R.id.place)

        placeButton.setOnClickListener {
            placeModel()
        }
        modelNode = ArModelNode(arView.engine, PlacementMode.INSTANT).apply {
            loadModelGlbAsync(
                glbFileLocation = "models/tire.glb",
                scaleToUnits = 1f,
                centerOrigin = Position(-0.5f)

            )
            {
                arView.planeRenderer.isVisible = true
                val materialInstance = it.materialInstances[0]
            }
            onAnchorChanged = {
                placeButton.isGone = it != null
            }

        }
        arView.addChild(modelNode)

    }
    private fun placeModel(){
        modelNode.anchor()

        arView.planeRenderer.isVisible = false

    }

}
