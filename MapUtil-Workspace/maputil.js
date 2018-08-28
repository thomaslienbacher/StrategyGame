// this code is cancer

const SET_COLOR = "#2eee2e"
const SELECTED_COLOR = "#fe1e1e"
const DEFAULT_STYLE = "fill:#ffdd55;fill-opacity:1;stroke:#feffff;stroke-width:0.73157227;stroke-miterlimit:4;stroke-dasharray:none;stroke-opacity:1"

var paths = []
var provinces = []
var ysmall = 10000000
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
		document.getElementById("province-color-r").value = provinces[id].color[0]
		document.getElementById("province-color-g").value = provinces[id].color[1]
		document.getElementById("province-color-b").value = provinces[id].color[2]
		document.getElementById("province-emblem").value = provinces[id].emblem
	}
	else {
		document.getElementById("province-name").value = ""
		document.getElementById("province-color-r").value = Math.round(Math.random() * 255)
		document.getElementById("province-color-g").value = Math.round(Math.random() * 255)
		document.getElementById("province-color-b").value = Math.round(Math.random() * 255)
		document.getElementById("province-emblem").value = ""
	}
	
	for (var i = 0; i < paths.length; i++) {
		paths[i].style = DEFAULT_STYLE
		if(provinces[i]) {
			if(provinces[i].name.localeCompare("") != 0 && provinces[i].emblem.localeCompare("") != 0) paths[i].style.fill = SET_COLOR
		} 
	}
	
	paths[id].style.fill = SELECTED_COLOR
}

function updateProvince() {
	var id = document.getElementById("province-id").value
	var name = document.getElementById("province-name").value
	var color_r = parseInt(document.getElementById("province-color-r").value)
	var color_g = parseInt(document.getElementById("province-color-g").value)
	var color_b = parseInt(document.getElementById("province-color-b").value)
	var emblem = document.getElementById("province-emblem").value
	
	var vertices = []
	var parts = paths[id].getAttribute("d").split(" ")
	var transform = paths[id].getAttribute("transform")
	var xpos = parseFloat(parts[1].split(",")[0])
	var ypos = parseFloat(parts[1].split(",")[1])
	
	for (var i = 2; i < parts.length; i++) {
		if(parts[i].localeCompare("z") == 0) continue
		if(parts[i].localeCompare("m") == 0) continue
		if(parts[i].localeCompare("l") == 0) continue
		
		if(parts[i].localeCompare("v") == 0) {
			i++
			
			ypos += parseFloat(parts[i])
			
			vertices.push(xpos)
			vertices.push(-ypos)
			continue
		}
		
		if(parts[i].localeCompare("h") == 0) {
			i++
			
			xpos += parseFloat(parts[i])
			
			vertices.push(xpos)
			vertices.push(-ypos)
			continue
		}
		
		xpos += parseFloat(parts[i].split(",")[0])
		ypos += parseFloat(parts[i].split(",")[1])
		
		vertices.push(xpos)
		vertices.push(-ypos)
	}
		
	if(transform) if(transform.startsWith("scale(")) {
		var scale = parseFloat(transform.substring(6, transform.length - 1))
		
		for (var i = 0; i < vertices.length; i++) {
			vertices[i] *= scale;
		}
	}
	
	provinces[id] = {"id": id, "name": name, "color": [color_r, color_g, color_b], "emblem": emblem, "vertices": vertices}
	if(name.localeCompare("") != 0 && emblem.localeCompare("") != 0) paths[id].style.fill = SET_COLOR
}

function generateJson() {
	for (var p = 0; p < provinces.length; p++) {
		if(!provinces[p]) continue
		var vertices = provinces[p].vertices
	
		for (var i = 0; i < vertices.length; i++) {
			i++
			if(vertices[i] < ysmall) ysmall = vertices[i]
		}
	}
	
	for (var p = 0; p < provinces.length; p++) {
		if(!provinces[p]) continue
		var vertices = provinces[p].vertices
	
		for (var i = 0; i < vertices.length; i++) {
			i++
			vertices[i] += -ysmall
		}
	}
	
	for (var p = 0; p < provinces.length; p++) {
		if(provinces[p]) {
			provinces[p].triangles = earcut(provinces[p].vertices, null, 2)
		}
	}
	
	document.getElementById("json-output").value = JSON.stringify(provinces)
}