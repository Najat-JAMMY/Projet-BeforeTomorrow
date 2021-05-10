/***************** MENU NAVIGATION *******************/



/***************** TEST POUR MQB MENU *******************/
/****************ATTENTION : SUPPRIME LA CARTE*********/

//Event on <li> cf capture d'écran
//mouse click

/*function(event) {
  event.preventDefault();
  var $el = $(this);

  if (!$el.parent().hasClass("active")) {
    that.$.container.find("li").removeClass("active");
    $el.parent().addClass("active");
    that.open({
      href: $el.attr("href")
    });
  } else {
    that.$.container.find("a.close").trigger("click");
  }
}


//mouse out
function() {
  var $bar = $(this).find("span.bar");
  TweenLite.to($bar, 0.15, {
    width: "0px",
    left: 0,
    ease: Quad.easeOut
  });
}

//mouse over
function() {
  var $bar = $(this).find("span.bar"),
    $barChildren = $bar.children();

  if ($toolbar.hasClass("closed")) {
    wantedLeft = "-140";
    $barChildren.css("text-align", "left");
  } else {
    wantedLeft = "0";
    $barChildren.css("text-align", "right");
  }
  TweenLite.to($bar, 0.15, {
    width: "200px",
    left: wantedLeft,
    ease: Quad.easeOut
  });
}*/



/************ SMOOTH SCROLL (FACULTATIF/ NECESSAIRE POUR INTERNET EXPLORER) *******************/
$(document).ready(function(){
	
  // Add smooth scrolling to all links
  $("a").on('click', function(event) {

    // Make sure this.hash has a value before overriding default behavior
    if (this.hash !== "") {
      // Prevent default anchor click behavior
      event.preventDefault();

      // Store hash
      var hash = this.hash;

      // Using jQuery's animate() method to add smooth page scroll
      // The optional number (800) specifies the number of milliseconds it takes to scroll to the specified area
      $('html, body').animate({
        scrollTop: $(hash).offset().top
      }, 800, function(){

        // Add hash (#) to URL when done scrolling (default click behavior)
        window.location.hash = hash;
      });
    } // End if
  });
});


/***************** CARTE DU MONDE *******************/
am4core.ready(function() {
    
    // Themes begin
    am4core.useTheme(am4themes_moonrisekingdom);
    am4core.useTheme(am4themes_animated);
    // Themes end

    
    // Create map instance
    var chart = am4core.create("chartdiv", am4maps.MapChart);
    
    // Set map definition
    chart.geodata = am4geodata_worldLow;
    chart.geodataNames = am4geodata_lang_FR;
    
    // Set projection
    chart.projection = new am4maps.projections.Miller();
    
    // Create map polygon series
    var polygonSeries = chart.series.push(new am4maps.MapPolygonSeries());
    
    // Exclude Antartica
    polygonSeries.exclude = ["AQ"];
    
    // Make map load polygon (like country names) data from GeoJSON
    polygonSeries.useGeodata = true;
    
    // Configure series
    var polygonTemplate = polygonSeries.mapPolygons.template;
    polygonTemplate.tooltipText = "{name}"; //bulles noms de pays
    polygonTemplate.fill = chart.colors.getIndex(0).lighten(0.4); //couleur fond de carte
    
    
    
    // Create hover state and set alternative fill color
    var hs = polygonTemplate.states.create("hover");
    hs.properties.fill = chart.colors.getIndex(0);
    
    // Add image series
    var imageSeries = chart.series.push(new am4maps.MapImageSeries());
    imageSeries.mapImages.template.propertyFields.longitude = "longitude";
    imageSeries.mapImages.template.propertyFields.latitude = "latitude";
    //*****************************ajout code bulles marker point******************************
    imageSeries.tooltipText = "{title}";
    
    //*****************************ajout code url******************************
    imageSeries.mapImages.template.propertyFields.url = "url"; //fonction concatenation pour lien vers le projet
	//appel fonction qui renvoie les data
	chargerDonneesCarteSitesFouille();
    imageSeries.data = [];
    
    // add events to recalculate map position when the map is moved or zoomed
    chart.events.on( "ready", updateCustomMarkers );
    chart.events.on( "mappositionchanged", updateCustomMarkers );
    
	// function
	function chargerDonneesCarteSitesFouille() {
		let request = new XMLHttpRequest();
		request.open("GET", "http://localhost:8080/map/coordsSitesFouille");
		request.responseType = "json";
		request.send();
		request.onload = () => {
			console.log(request);
			if(request.status === 200) {
				imageSeries.data = request.response;
			} else {
				console.log('error ${request.status} ${request.statusText}');
			}
		}
	}

    // this function will take current images on the map and create HTML elements for them
    function updateCustomMarkers( event ) {
      
      // go through all of the images
      imageSeries.mapImages.each(function(image) {
        // check if it has corresponding HTML element
        if (!image.dummyData || !image.dummyData.externalElement) {
          // create onex
          image.dummyData = {
            externalElement: createCustomMarker(image)
          };
        }
    
        // reposition the element according to coordinates
        var xy = chart.geoPointToSVG( { longitude: image.longitude, latitude: image.latitude } );
        image.dummyData.externalElement.style.top = xy.y + 'px';
        image.dummyData.externalElement.style.left = xy.x + 'px';
      });
    
    }
    
    // this function creates and returns a new marker element
    function createCustomMarker( image ) {
      
      var chart = image.dataItem.component.chart;

      // create holder
      var holder = document.createElement( 'div' );
      holder.className = 'map-marker';
      holder.title = image.dataItem.dataContext.title;
      holder.style.position = 'absolute';
    
      // maybe add a link to it?
      if ( undefined != image.url ) {
        holder.onclick = function() {
          window.location.href = image.url;
        };
        holder.className += ' map-clickable';
      }
      

      //Couleur Pointeurs
      var color = image.dataItem.dataContext.color;
      // create dot 
      var dot = document.createElement( 'div' );
      dot.className = 'dot';
      if(color){
        dot.style.borderColor = color;
      }
      holder.appendChild( dot );

      // create pulse
      var pulse = document.createElement( 'div' );
      pulse.className = 'pulse';
      if(color){
          pulse.style.borderColor = color;
          pulse.style.backgroundColor = color;
      }
      holder.appendChild( pulse );
    
      // append the marker to the map container
      chart.svgContainer.htmlElement.appendChild( holder );
    
      return holder;
    }
    
    }); // end am4core.ready()


