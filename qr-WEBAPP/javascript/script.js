$(document).ready(function() {
    

    $('#logout').click(()=>{
        localStorage.removeItem('user');
        localStorage.removeItem('email');
    });

    $('#loginform').on('submit',(event)=>{
            event.preventDefault();
            let reqJSON =  '{"emailDomain":"'+$('#loginform input')[0].value+'","password":"'+$('#loginform input')[1].value+'"}';
            $.ajax({
            url: "http://localhost:8080/qr-web-0.0.1-SNAPSHOT/account/login",
            type: "POST",
            contentType: 'application/json; charset=utf-8',
            data:reqJSON
            }).done(function(res){
                res = JSON.parse(res);
                if(res.login==true){
                    localStorage.setItem('user',res.id);
                    localStorage.setItem('email',res.email);
                    window.location.href = "allContacts.html";
                }
                else{
                    console.log("log in failed");
                }
                
                });    
    });

    $('.modal').on('hidden.bs.modal', function(){
        $(this).find('form')[0].reset();
    });

    
    

        var table = $('#dataContacts').DataTable( {
            "processing":true,
            "ajax": "http://localhost:8080/qr-web-0.0.1-SNAPSHOT/contact/"+localStorage.getItem('user'),
            "columns": [
                {data: null, render:(data,type,row)=>{
                    return data.firstName+' '+data.lastName;
                } },
                
                { "data": "email" },
                
                {data: null, render:(data,type,row)=>{
                    return data.city+', '+data.state+', '+data.country;
                } },
                {
                    data: null,
                    targets:-1,
                    defaultContent: '<button class="editor_edit btn btn-primary">Edit</button> <button class="editor_remove btn btn-danger">Delete</button>'
                }
                
            ]
            } );


        $( "#contactForm" ).on( "submit", function( event ) {
            event.preventDefault();
            var serializedArr;
            var properJsonObj;
            

            if($('#contact-modal').attr('name')=='addnew'){
                var serializedArr = $("#contactForm").serializeArray();

                var properJsonObj = jQFormSerializeArrToJson(serializedArr);

                //console.log(properJsonObj);

                let data = {
                    "firstName": properJsonObj.firstName,
                    "lastName": properJsonObj.lastName,
                    "email": properJsonObj.email,
                    "gender": null,
                    "phoneNumber": properJsonObj.phoneNumber,
                    "status": null,
                    "streetAddress": properJsonObj.streetAddress,
                    "city": properJsonObj.city,
                    "state": properJsonObj.state,
                    "country": properJsonObj.country,
                    "account": {
                        "id": localStorage.getItem('user')
                    }
                }             
                data = JSON.stringify(data);     
            $.ajax({
            url: "http://localhost:8080/qr-web-0.0.1-SNAPSHOT/contact",
            type: "POST",
            contentType: "application/json;charset=utf-8",
            data:data
            }).done((response)=>{
                $('#contact-modal').modal('hide');
                table.ajax.reload( null, false );
            });
            }
            });
        

          function jQFormSerializeArrToJson(formSerializeArr){
            var jsonObj = {};
            jQuery.map( formSerializeArr, function( n, i ) {
                jsonObj[n.name] = n.value;
            });
           
            return jsonObj;
           }

 
    // Edit record
    $('#dataContacts').on('click', 'button.editor_edit', function (e) {
        //console.log(table.row( $(this).parents('tr') ).data());
        let data = table.row( $(this).parents('tr') ).data();

        $('input[name=firstName]').val(table.row( $(this).parents('tr') ).data().firstName);
        $('input[name=lastName]').val(table.row( $(this).parents('tr') ).data().lastName);
        $('input[name=email]').val(table.row( $(this).parents('tr') ).data().email);
        $('input[name=phoneNumber]').val(table.row( $(this).parents('tr') ).data().phoneNumber);
        $('input[name=streetAddress]').val(table.row( $(this).parents('tr') ).data().streetAddress);
        $('input[name=city]').val(table.row( $(this).parents('tr') ).data().city);
        $('input[name=state]').val(table.row( $(this).parents('tr') ).data().state);
        $('input[name=country]').val(table.row( $(this).parents('tr') ).data().country);
        $('#contact-modal').modal('show');
        $('#contact-modal').attr('name','update');
        
        $( "#contactForm" ).on( "submit", function( event ) {
            event.preventDefault();
            var serializedArr;
            var properJsonObj;
            

            if($('#contact-modal').attr('name')=='update'){
                var serializedArr = $("#contactForm").serializeArray();

                var properJsonObj = jQFormSerializeArrToJson(serializedArr);

                //console.log(properJsonObj);

                data.firstName = properJsonObj.firstName;
                data.lastName = properJsonObj.lastName;
                data.city = properJsonObj.city;
                data.country = properJsonObj.country;
                data.phoneNumber = properJsonObj.phoneNumber;
                data.state = properJsonObj.state;
                data.streetAddress = properJsonObj.streetAddress;
                data.email = properJsonObj.email;              
                data = JSON.stringify(data);     
            $.ajax({
            url: "http://localhost:8080/qr-web-0.0.1-SNAPSHOT/contact",
            type: "PUT",
            contentType: "application/json;charset=utf-8",
            data:data
            }).done((response)=>{
                $('#contact-modal').modal('hide');
                table.ajax.reload( null, false );
            });
                $('#contact-modal').attr('name','addnew');
            }
          });
        
    } );
 
    // Delete a record
    $('#dataContacts').on('click', 'button.editor_remove', function (e) {
        e.preventDefault();
        let check = confirm("Confirm to Delete User : "+table.row( $(this).parents('tr') ).data().email);
        if(check==true){
            $.ajax({
            url: "http://localhost:8080/qr-web-0.0.1-SNAPSHOT/contact/"+table.row( $(this).parents('tr') ).data().id,
            type: "DELETE",
            }).done((response)=>{
                table.ajax.reload( null, false );
            });
            
        }
    } );

} );
function myFunction(){
    $('#useremail').text(localStorage.getItem('email'));
}