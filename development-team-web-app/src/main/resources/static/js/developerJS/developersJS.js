 $('#deleteDialog').on('show.bs.modal', function (event) {
        var target = $(event.relatedTarget)
        $(this).find('.modal-body').text('Please confirm delete developer: "' + target.data('name') + '"')
        document.getElementById('deleteUrl').href = 'developers/' + target.data('id') + '/delete';
    })
    $(function () {
        $('[data-toggle="tooltip"]').tooltip()
    })

