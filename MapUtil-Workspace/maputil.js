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
		document.getElementById("province-emblem").value = provinces[id].emblem
	}
	else {
		document.getElementById("province-name").value = ""
		document.getElementById("province-emblem").value = ""
	}
	
	for (var i = 0; i < paths.length; i++) {
		paths[i].style = DEFAULT_STYLE
		if(provinces[i]) if(provinces[i].name.localeCompare("") != 0 && provinces[i].emblem.localeCompare("") != 0) paths[i].style.fill = SET_COLOR
	}
	
	paths[id].style.fill = SELECTED_COLOR
}

function updateProvince() {
	var id = document.getElementById("province-id").value
	var name = document.getElementById("province-name").value
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
		
		console.log(scale)
		
		for (var i = 0; i < vertices.length; i++) {
			vertices[i] *= scale;
		}
	}
	
	provinces[id] = {"id": id, "name": name, "emblem": emblem, "vertices": vertices}
	if(provinces[id].name.localeCompare("") != 0 && provinces[id].emblem.localeCompare("") != 0) paths[id].style.fill = SET_COLOR
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
		if(provinces[p]) provinces[p].triangles = earcut(provinces[p].vertices, null, 2)
	}
	
	document.getElementById("json-output").value = JSON.stringify(provinces)
}