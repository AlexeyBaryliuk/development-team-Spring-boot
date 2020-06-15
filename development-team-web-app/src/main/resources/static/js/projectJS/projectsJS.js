
 $('#deleteDialog').on('show.bs.modal', function (event) {
        var target = $(event.relatedTarget)
        $(this).find('.modal-body').text('Please confirm delete project: "' + target.data('id') + '"')
        document.getElementById('deleteUrl').href = 'projects/' + target.data('id') + '/delete';
    })
    $(function () {
        $('[data-toggle="tooltip"]').tooltip()
    });

$('#descriptionProject').on('show.bs.modal', function (event) {
        var target = $(event.relatedTarget)
        $(this).find('.modal-body').text('"' + target.data('desc') + '"')
    })
    $(function () {
        $('[data-toggle="tooltip"]').tooltip()
    });


$("#excel").on('change', function(){
    if($(this).val() == 1){
        $("#input-box").show();
    } else if($(this).val() == 2){
        $("#input-box").hide();
        var option = $(this).find('option:selected');
        window.location.href = option.data("url");

    }else{
        $("#input-box").hide();
    }

});

$(document).on('change', '#input', function() {
    var file = $(this)[0]; // note the [0] to return the DOMElement from the jQuery object
    var test = 'Event Triggered';

    console.log(test);
    console.log(file.files);
    console.log(file.files[0]);
});