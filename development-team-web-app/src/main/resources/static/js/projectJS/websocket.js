'use strict';

var body = document.querySelector('#bodyt');
var table = document.querySelector('#tab');
var refresh = document.querySelector('#refresh');
var deleteProject = document.querySelector('#deleteUrl');
var deleteDialog = document.querySelector('#deleteDialog');

var stompClientAll = null;
var stompClientDelete = null;
var stompClientAdd = null;
var stompClientUpdate = null;
var stompClientFilter = null;

var delProjectId = null;
var message = null;

    function connect(event) {

//    addProject.removeEventListener('click', connect, true);

        var socketAll = new SockJS('/ws');
        var socketDelete = new SockJS('/ws');
        var socketAdd = new SockJS('/ws');
        var socketUpdate = new SockJS('/ws');
        var socketFilter = new SockJS('/ws');
            stompClientAll = Stomp.over(socketAll);
            stompClientDelete = Stomp.over(socketDelete);
            stompClientAdd = Stomp.over(socketAdd);
            stompClientUpdate = Stomp.over(socketUpdate);
            stompClientFilter = Stomp.over(socketFilter);

        console.log('_____________________HELLO CONNECT');

        stompClientAll.connect({}, onConnectedAll, onError);
        stompClientDelete.connect({}, onConnectedDelete, onError);
        stompClientAdd.connect({}, onConnectedAdd, onError);
        stompClientUpdate.connect({}, onConnectedUpdate, onError);
        stompClientFilter.connect({}, onConnectedFilter, onError);
        event.preventDefault();
    }

    function onConnectedAll() {
        console.log('_____________________onConnectedAll');
            // Subscribe to the Public Topic
        stompClientAll.subscribe('/topic/all', onAllProjectsReceived);
    }

    function onConnectedDelete() {
        console.log('_____________________onConnectedDelete');
            // Subscribe to the Public Topic
        stompClientDelete.subscribe('/topic/delete', onDeleteProject);

    }

    function onConnectedAdd() {
        console.log('_____________________onConnectedAdd');
                // Subscribe to the Public Topic
        stompClientAdd.subscribe('/topic/add', onAddProject);
    }

    function onConnectedUpdate() {
        console.log('_____________________onConnectedUpdate');
                // Subscribe to the Public Topic
        stompClientUpdate.subscribe('/topic/update', onUpdateProject);
    }

    function onConnectedFilter() {
        console.log('_____________________onConnectedUpdate');
                // Subscribe to the Public Topic
        stompClientFilter.subscribe('/topic/filter', onAllProjectsReceived);
    }

    function onError(error) {
        connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
        connectingElement.style.color = 'red';
    }

    function deleteProjectFun(e){

        stompClientDelete.send("/app/delete", {}, JSON.stringify(delProjectId));

    }

    function onDeleteProject(payload){

        message = payload.body;
        if(message == 'delete'){
         console.log('_____________________HELLO DELETE = '  + message);

            stompClientAll.send("/app/chat.getAll");
        };
    }

    function onAddProject(payload){

        var project = JSON.parse(payload.body);
        var addBody = table.getElementsByTagName("tbody")[0];
        if(project){
            var res = getListContent(project);
            addBody.append(res);
        };
    }

    function onUpdateProject(payload){

        var project = JSON.parse(payload.body);
        if(project){
            var res = getListContent(project);
        };
        for (var j = 1;  j < table.rows.length; j++){
            if(table.rows [j].cells [0].innerHTML == project.projectId.toString() ){
            table.rows [j].outerHTML = res.outerHTML;
            };
        };
    }

    function onRefresh(){

    stompClientAll.send("/app/chat.getAll");
    }

    function onAllProjectsReceived(payload) {

        table.removeChild(table.getElementsByTagName("tbody")[0]);

        var newBody = document.createElement('tbody');
        var allProjectsReceived = JSON.parse(payload.body);

        for(var i = 0; i < allProjectsReceived.length; i++){

            var res = getListContent(allProjectsReceived, i);
            newBody.append(res);
            table.append(newBody);
        };
    }


    function getListContent(message, j) {

        var tempMessage = null;
        if(j != null){
        tempMessage = message[j];
        }
        else{
        tempMessage = message;
        }
        var messageElement = document.createElement('tr');
        var trElement1 = document.createElement('td');
            trElement1.append(tempMessage.projectId);
            messageElement.append(trElement1);
        var trElement2 = document.createElement('td');
            trElement2.append(tempMessage.dateAdded);
            messageElement.append(trElement2);
        var trElement3 = document.createElement('td');
            trElement3.append(tempMessage.countOfDevelopers);
            messageElement.append(trElement3);
        var trElement4 = document.createElement('td');
            trElement4.className ="text-right";
            trElement4.innerHTML =
            " <a href=\"/projects/" + tempMessage.projectId + "\" " +
             "class=\"btn-sm btn-outline-secondary\"" +
                                            "title=\"Edit project\"" +
                                            "data-toggle=\"tooltip\"" +
                                            "data-placement=\"top\">" +
                                             "<i class=\"fa fa-pencil\"></i> <span" +
                                                 "class=\"d-none d-md-inline\">Edit</span></a>" +
                                         "<span data-toggle=\"modal\"" +
                                               "data-target=\"#deleteDialog\"" +
                                               "data-id= \"" + tempMessage.projectId + "\"  data-date= \"" + tempMessage.dateAdded +"\">" +
                                         "<a href=\"#\" class=\"btn-sm btn-outline-secondary\"" +
                                            "title=\"delete project\"" +
                                            "data-toggle=\"tooltip\"" +
                                            "data-placement=\"top\">" +
                                             "<i class=\"fa fa-remove\"></i> <span class=\"d-none d-md-inline\">Delete</span></a>" +
                                         "</span>" +
                                         "<span data-toggle=\"modal\"" +
                                               "data-target=\"#descriptionProject\"" +
                                               "data-desc=\"" + tempMessage.description + "\">" +
                                        " <a href=\"#\"" +
                                            "class=\"btn-sm btn-outline-secondary\"" +
                                            "title=\"Description\"" +
                                            "data-toggle=\"modal\"" +
                                            "data-placement=\"bottom\">" +
                                             "<i class=\"fa fa-eye\"></i>" +
                                             "<span class=\"d-none d-sm-inline\">Description</span></a>" +
                                         "</span>" +
                                         "<div class=\"modal\" id=\"descriptionProject\">" +
                                             "<div class=\"modal-dialog\">" +
                                                 "<div class=\"modal-content\">" +
                                                     "<div class=\"modal-header\">" +
                                                         "<h4 class=\"modal-title\">Description</h4>" +
                                                         "<button type=\"button\" class=\"close\" data-dismiss=\"modal\">×</button>" +
                                                     "</div>" +
                                                     "<div class=\"modal-body\">" +
                                                         "Modal body.." +
                                                    " </div>" +
                                                     "<div class=\"modal-footer\">" +
                                                         "<button type=\"button\" class=\"btn btn-info\" data-dismiss=\"modal\">Close</button>" +
                                                     "</div>" +
                                                 "</div>" +
                                             "</div>" +
                                         "</div>";
        messageElement.append(trElement4);
     return messageElement;
    }

     $('#deleteDialog').on('show.bs.modal', function (event) {

            var target = $(event.relatedTarget)
            delProjectId = target.data('id')
            $(this).find('.modal-body').text('Please confirm delete project: "' + target.data('id') + '"')

        });

    window.addEventListener("load",connect, true);
    refresh.addEventListener('click', onRefresh, true );
    deleteProject.addEventListener('click', deleteProjectFun, true );