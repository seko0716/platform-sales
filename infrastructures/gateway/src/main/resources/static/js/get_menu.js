$(document).ready(function () {
    $.ajax({
        url: rootUrl + "http://localhost:8081/rest/menu"
    }).then(function (data) {
        document.getElementById('addAirf').setAttribute('href', rootUrl + "/adminka/addAirfoil.html");
        document.getElementById('adm').setAttribute('href', rootUrl + "/adminka/adminka.html");
        data.forEach(logArrayElements);

        function logArrayElements(element, index, array) {
            console.log(element.header);
            // элемент-список UL
            var list = document.getElementById('list_menu');
            // новый элемент
            var header = document.createElement('p');
            header.innerText = element.header;
            header.setAttribute('class', 'lead');
            // добавление в конец
            list.appendChild(header);


            var list_group = document.createElement('div');
            list_group.setAttribute('class', 'list-group');
            element.menuItems.forEach(logMenuElements)


            function logMenuElements(element, index, array) {
                // console.log(element.name + '   ' + element.url);

                // новый элемент
                var menu_item = document.createElement('a');
                menu_item.setAttribute('class', 'list-group-item');
                menu_item.innerText = element.name;
                menu_item.setAttribute('href', rootUrl + '/airfoilList.html?prefix=' + element.urlCode);

                list_group.appendChild(menu_item);
            }

            list.appendChild(list_group);

        }


    });
});
