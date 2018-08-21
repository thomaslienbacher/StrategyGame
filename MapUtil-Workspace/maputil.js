var paths = null
var provinces = null

function setSVG() {
	var file = document.getElementById("svg-file").files[0]
	
	if (file) {
		var reader = new FileReader()
		reader.readAsText(file, "UTF-8")
		reader.onload = function (evt) {
			document.getElementById("view").innerHTML = evt.target.result
			prepareSVG();
		}
		reader.onerror = function (evt) {
			document.getElementById("view").innerHTML = "error reading file"
		}
	}
}

function prepareSVG() {
	paths = document.getElementsByTagName("svg")[0].getElementsByTagName("path")

	
	for (var i = 0; i < paths.length; i++) {
		console.log(i)
		 const d = i
		
		paths[i].addEventListener("click", function() {
			selectPath(d)
		})
		
		paths[i].style = "fill:#ffdd55;fill-opacity:1;stroke:#feffff;stroke-width:0.73157227;stroke-miterlimit:4;stroke-dasharray:none;stroke-opacity:1"
	}
}

function selectPath(id) {
	document.getElementById("selected-path").value = id
	
	for (var i = 0; i < paths.length; i++) {
		paths[i].style = "fill:#ffdd55;fill-opacity:1;stroke:#feffff;stroke-width:0.73157227;stroke-miterlimit:4;stroke-dasharray:none;stroke-opacity:1"
	}
	
	paths[id].style.fill = "#2eee2e"
}