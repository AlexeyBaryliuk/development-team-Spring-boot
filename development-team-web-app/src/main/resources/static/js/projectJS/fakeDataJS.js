$(document).ready(()=>{
    $("#changePro").click(()=>{
        var number = document.getElementById('number').value;
        var locale = document.getElementById('changeJS').value;
       document.getElementById('changePro').href = 'changeTestData/projects?locale=' + locale +'&' + 'number=' + number ;
    });

    $("#changeDev").click(()=>{
        var number = document.getElementById('number').value;
        var locale = document.getElementById('changeJS').value;
       document.getElementById('changeDev').href = 'changeTestData/developers?locale=' + locale +'&' + 'number=' + number ;
    });
});