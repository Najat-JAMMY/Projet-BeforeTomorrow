am4core.ready(function() {

// Themes begin
am4core.useTheme(am4themes_moonrisekingdom);
am4core.useTheme(am4themes_animated);
// Themes end

// Create chart instance
var chart = am4core.create("chartdivProjet", am4charts.PieChart);

// Add data
chargerDonneesStat();

chart.data = [];
function chargerDonneesStat() {
	let request = new XMLHttpRequest();
	request.open("GET", "http://localhost:8080/stat/coordsStatProjet");
	request.responseType = "json";
	request.send();
	request.onload = () => {
		console.log(request);
		if(request.status === 200) {
			chart.data = request.response;
		} else {
			console.log('error ${request.status} ${request.statusText}');
		}
	}
}


// Add and configure Series
var pieSeries = chart.series.push(new am4charts.PieSeries());
pieSeries.dataFields.value = "litres";
pieSeries.dataFields.category = "country";
pieSeries.slices.template.stroke = am4core.color("#fff");
pieSeries.slices.template.strokeWidth = 2;
pieSeries.slices.template.strokeOpacity = 1;

// This creates initial animation
pieSeries.hiddenState.properties.opacity = 1;
pieSeries.hiddenState.properties.endAngle = -90;
pieSeries.hiddenState.properties.startAngle = -90;




}); // end am4core.ready()