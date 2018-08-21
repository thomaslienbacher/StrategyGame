const SET_COLOR = "#2eee2e"
const SELECTED_COLOR = "#fe1e1e"
const DEFAULT_STYLE = "fill:#ffdd55;fill-opacity:1;stroke:#feffff;stroke-width:0.73157227;stroke-miterlimit:4;stroke-dasharray:none;stroke-opacity:1"

var paths = []
var provinces = []
var states = []

function setSVG() {
	var file = document.getElementById("svg-file").files[0]
	
	if (file) {
		var reader = new FileReader()
		reader.readAsText(file, "UTF-8")
		reader.onload = function(evt) {
			document.getElementById("view").innerHTML = evt.target.result
			prepareSVG();
		}
		reader.onerror = function(evt) {
			document.getElementById("view").innerHTML = "Error reading file"
		}
	}
}

function prepareSVG() {
	paths = document.getElementsByTagName("svg")[0].getElementsByTagName("path")

	for (var i = 0; i < paths.length; i++) {
		const d = i
		
		paths[i].addEventListener("click", function() {
			selectPath(d)
		})
		
		paths[i].style = DEFAULT_STYLE
	}
}

function selectPath(id) {
	document.getElementById("selected-path").value = id
	document.getElementById("province-id").value = id
	
	if(provinces[id]) {
		document.getElementById("province-name").value = provinces[id].name
		//document.getElementById("province-occupier").value = provinces[id].occupier
	}
	else {
		document.getElementById("province-name").value = ""
		//document.getElementById("province-occupier").value = ""
	}
	
	for (var i = 0; i < paths.length; i++) {
		paths[i].style = DEFAULT_STYLE
		if(provinces[i]) paths[i].style.fill = SET_COLOR
	}
	
	paths[id].style.fill = SELECTED_COLOR
}

function updateProvince() {
	var id = document.getElementById("province-id").value
	var name = document.getElementById("province-name").value
	//var occupier = document.getElementById("province-occupier").value
	
	var vertices = []
	var parts = paths[id].getAttribute("d").split(" ")
	var xtrans = parseFloat(parts[1].split(",")[0])
	var ytrans = parseFloat(parts[1].split(",")[1])
	
	for (var i = 2; i < parts.length; i++) {
		if(parts[i].localeCompare("z") == 0) continue
		if(parts[i].localeCompare("m") == 0) continue
		
		if(parts[i].localeCompare("h")  == 0) {
			i++
			vertices.push(parseFloat(vertices[vertices.length - 2]) + xtrans)
			vertices.push(parseFloat(parts[i]) + ytrans)
			continue
		}
		
		vertices.push(parseFloat(parts[i].split(",")[0]) + xtrans)
		vertices.push(parseFloat(parts[i].split(",")[1]) + ytrans)
	}
	
	provinces[id] = {"id": id, "name": name, /*"occupier": occupier,*/ "vertices": vertices}
	paths[id].style.fill = SET_COLOR
}

function generateJson() {
	document.getElementById("json-output").value = JSON.stringify(provinces)
}