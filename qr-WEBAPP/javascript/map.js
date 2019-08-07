function initMap() {
    var map = new google.maps.Map(document.getElementById('map'), {
      zoom: 8,
      center: {lat: -34.397, lng: 150.644}
    });
    var geocoder = new google.maps.Geocoder();
    $.ajax({
        url: "http://localhost:8080/qr-web-0.0.1-SNAPSHOT/contact/"+localStorage.getItem('user'),
        type: "GET"
        }).done((response)=>{
            let users=[];
            response.data.map((user)=>{
                users.push({"address":user.streetAddress+","+user.city+","+user.state+","+user.country,"data":"<h4>"+user.firstName+" "+user.lastName+"</h4><p>"+user.email+"</p>"});
            });
            users.map((user)=>{
                geocodeAddress(geocoder, map,user);
            });

        });
  }

  function geocodeAddress(geocoder, resultsMap,user) {
    geocoder.geocode({'address': user.address}, function(results, status) {
      if (status === 'OK') {
        resultsMap.setCenter(results[0].geometry.location);
        var marker = new google.maps.Marker({
          map: resultsMap,
          position: results[0].geometry.location
        });
        var infoWindow = new google.maps.InfoWindow({
        content:user.data
        });
        marker.addListener('click', function(){
        infoWindow.open(map, marker);
      });
      } else {
        alert('Geocode was not successful for the following reason: ' + status);
      }
    });
  }

  $('#logout').click(()=>{
      localStorage.removeItem('user');
      localStorage.removeItem('email');
  });

  function myFunction(){
    $('#useremail').text(localStorage.getItem('email'));
}