<template>
  <div class="page">
    <div class="panel">
      <div id="map" class="map-content">
        <canvas id="canvas"></canvas>
      </div>
      <br />
      <div class="map-navigation">
        <div class="map-navigation-item">
          <button @click="placeRobotStart">Place Robot Start Position</button>
        </div>
        <div class="map-navigation-item">
          <button @click="placeTable">Place A Table</button>
        </div>
        <div class="map-navigation-item">
          <button @click="placeWall">Place A Wall</button>
        </div>

        <div class="map-navigation-item">
          <button class="danger-action" @click="deleteSelected">
            Delete Selected
          </button>
        </div>
        <div id="scaleView" ref="scaleView">
            Map Scale: 
            <div id="scale">
                &nbsp;
            </div> = {{customScale}}cm
        </div>
        <div id="customScale" class="map-navigation-item">
            Custom Scale: <input type="number" v-model="customScale">
        </div>
        <div class="map-navigation-item">
            <button @click="saveScale">Save</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { fabric } from "fabric";

let canvas;
let clientHeight;
let clientWidth;
var unitScale = 10;
const barFill = "rgba(0, 93, 127, 0.7)";
const barStroke = "#003e54";
const wallFill = "rgba(136, 136, 136, 0.7)";
const wallStroke = "#686868";

export default {
  data() {
    return {
      canvasZoom: 0,
      nummm: 0,
      customScale: 50
    };
  },
  setup() {},
  mounted() {
    this.initCanvas();

    if(localStorage.getItem("scale"))
        this.customScale = localStorage.getItem("scale")
  },
  methods: {
    genId() {
      return "_" + Math.random().toString(36).substr(2, 9);
    },
    saveScale(){
        localStorage.setItem("scale", this.customScale)
    },
    initCanvas() {
      clientHeight = document.getElementById("map").clientHeight;
      clientWidth = document.getElementById("map").clientWidth;

     var grid = 50;

      canvas = new fabric.Canvas("canvas", {});

      canvas.setWidth(clientWidth);
      canvas.setHeight(clientHeight);

      if (localStorage.getItem("mapZoom")) {
        this.canvasZoom = parseFloat(localStorage.getItem("mapZoom"));
        canvas.setZoom(this.canvasZoom);
      }

      canvas.on("mouse:wheel", function (opt) {
        var delta = opt.e.deltaY;
        this.canvasZoom = canvas.getZoom();
        this.canvasZoom *= 0.999 ** delta;
        if (this.canvasZoom > 5) this.canvasZoom = 5;
        if (this.canvasZoom < 1) this.canvasZoom = 1;
        canvas.setZoom(this.canvasZoom);
        opt.e.preventDefault();
        opt.e.stopPropagation();

        localStorage.setItem("mapZoom", this.canvasZoom);
      });

      canvas.on("mouse:down", () => this.handleSave());

      var canvasData = localStorage.getItem("mapConfig");

      if (canvasData) {
        canvas.loadFromJSON(canvasData);
      }

      // create grid
      for (var i = 0; i < clientWidth / grid; i++) {
        canvas.add(
          new fabric.Line([i * grid, 0, i * grid, clientHeight], {
            type: "line",
            stroke: "#gray",
            selectable: false,
            hasControl: false
          })
        );
        canvas.add(
          new fabric.Line([0, i * grid, clientWidth, i * grid], {
            type: "line",
            stroke: "#gray",
            selectable: false,
            hasControl: false
          })
        );
      }

      // snap to grid
      canvas.on("object:moving", function (options) {
        options.target.set({
          left: Math.round(options.target.left / grid) * grid,
          top: Math.round(options.target.top / grid) * grid,
        });
      });
    },
    placeRobotStart() {
      const o = new fabric.Circle({
        radius: 10,
        fill: "gray",
        left: 10,
        right: 10,
        top: 10,
        id: this.genId(),
      });
      canvas.add(o);
    },
    placeTable() {
      const o = new fabric.Rect({
        width: 5 * unitScale,
        height: 5 * unitScale,
        fill: barFill,
        stroke: barStroke,
        strokeWidth: 2,
        originX: "center",
        originY: "center",
        id: this.genId(),
      });

      const t = new fabric.IText("Table", {
        fontFamily: "Calibri",
        fontSize: 14,
        fill: "#fff",
        textAlign: "center",
        originX: "center",
        originY: "center",
        id: this.genId(),
      });

      const g = new fabric.Group([o, t], {
        left: 10,
        top: 10,
        centeredRotation: true,
        snapAngle: 45,
        selectable: true,
      });

      canvas.add(g);
    },
    placeWall() {
      const o = new fabric.Rect({
        left: 10,
        top: 10,
        width: 100,
        height: 50,
        fill: wallFill,
        stroke: wallStroke,
        strokeWidth: 2,
        originX: "center",
        originY: "center",
        centeredRotation: true,
        snapAngle: 45,
        selectable: true,
        id: this.genId(),
      });

      const t = new fabric.IText("Wall", {
        fontFamily: "Calibri",
        fontSize: 14,
        fill: "#fff",
        textAlign: "center",
        originX: "center",
        originY: "center",
      });

      const g = new fabric.Group([o, t], {
        left: 100,
        top: 50,
        centeredRotation: true,
        snapAngle: 45,
        selectable: true,
      });

      canvas.add(g);
    },
    deleteSelected() {
      var activeOBj = canvas.getActiveObject();

      if (activeOBj) {
        canvas.remove(activeOBj);
        canvas.discardActiveObject();
        canvas.renderAll();
      }
    },
    handleSave() {
      localStorage.setItem("mapConfig", "");
      var json = JSON.stringify(canvas);
      localStorage.setItem("mapConfig", json);
    },
    exportAsYAML() {
      console.log("Export to yaml");
    },
  },
};
</script>

<style scoped>
#scaleView{
    margin-left: 15px;
    margin-top: 20px;
}
#scale{
    width: 20px;
    height: 20px;
    background-color: white;
    border: 1px solid black;
    display: inline-block;
}
.canvas-container {
  width: 100% !important;
  height: 100% !important;
  background-color: red;
}

canvas {
  width: 100% !important;
  height: 100% !important;
  border: 1px solid var(--text-color);
}
.page {
  top: 0;
  bottom: 0;
  right: 0;
  margin: auto;
  height: fit-content;
  width: fit-content;
}

.map-content {
  height: 25rem;
  min-width: 100%;
}

.map-navigation {
  height: 5rem;
  min-width: 100%;
  display: inline-grid;
  grid-template-columns: auto auto auto auto;
}

.map-navigation-item {
  margin-top: 20px;
  text-align: center;
  width: 100%;
}

.panel {
  min-width: 875px;
  width: 40vw;
  min-height: 525px;
  max-height: 60vh;
  padding: 20px;
  background-color: var(--secondary-color);
  box-shadow: 5px 5px 5px 1px rgba(0, 0, 0, 0.5);
  border-radius: 10px;
  display: flex;
  flex-direction: column;
  align-items: center;
  overflow-x: hidden;
  overflow-y: overlay;
}

button {
  height: 40px;
  min-width: 180px;
  font-family: Strait;
  font-weight: 400;
  font-style: normal;
  font-size: 1rem;
  margin: 0 auto;
  color: var(--secondary-color);
  background: var(--primary-color);
  border-radius: 5px;
  border: none;
  display: flex;
  justify-content: center;
  align-items: center;
  transition: 0.2s ease;
}

.danger-action {
  background-color: red !important;
}

button:hover {
  transform: scale(1.025);
  box-shadow: 5px 5px 4px rgba(0, 0, 0, 0.25);
  cursor: pointer;
}

.panel::-webkit-scrollbar {
  width: 5px;
}

.panel::-webkit-scrollbar-trac {
  margin: 20px;
}

.panel::-webkit-scrollbar-thumb {
  border-radius: 3px;
  background: var(--primary-color);
  background-clip: padding-box;
}

.panel::-webkit-scrollbar-thumb:hover {
  opacity: 0.9;
}
</style>