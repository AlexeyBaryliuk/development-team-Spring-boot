
$("#excel").on('change', function(){
var select = document.querySelector("#excel")
    if($(this).val() == 1){
        $("#input-box").show();
        $("#button-box").hide();
    } else if($(this).val() == 2){
        $("#input-box").hide();
        $("#button-box").show();
//        var option = $(this).find('option:selected');
//        window.location.href = option.data("url");
    }
 select.selectedIndex = 0;
});

$(document).on('change', '#input', function() {
    var file = $(this)[0]; // note the [0] to return the DOMElement from the jQuery object
    var test = 'Event Triggered';

    console.log(test);
    console.log(file.files);
    console.log(file.files[0]);
});