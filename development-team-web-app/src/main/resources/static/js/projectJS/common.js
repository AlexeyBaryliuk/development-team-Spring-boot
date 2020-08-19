
//    $('#deleteDialog').on('show.bs.modal', function (event) {
//        var target = $(event.relatedTarget)
//        $(this).find('.modal-body').text('Please confirm delete project: "' + target.data('id') + '"')
//        document.getElementById('deleteUrl').href = 'projects/' + target.data('id') + '/delete';
//    })
    $(function () {
        $('[data-toggle="tooltip"]').tooltip()
    })


    $('#descriptionProject').on('show.bs.modal', function (event) {
        var target = $(event.relatedTarget)
        $(this).find('.modal-body').text('"' + target.data('desc') + '"')
    })
    $(function () {
        $('[data-toggle="tooltip"]').tooltip()
    })
