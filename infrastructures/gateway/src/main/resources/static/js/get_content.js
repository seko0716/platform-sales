var currentPrefix = '';

function getContent() {
    var prefix = $.getUrlVar('prefix') != undefined ? $.getUrlVar('prefix') : 'A';
    var no = $.getUrlVar('no') != undefined ? $.getUrlVar('no') - 1 : 0;

    console.log("no=" + no);

    console.log(currentPrefix);
    if (currentPrefix !== '') {
        console.log('updatePage');
        document.getElementById("contentid").removeChild(document.getElementById("airfoil_list"));
    }
    currentPrefix = prefix;
    $(document).ready(function () {
        $.ajax({
            url: rootUrl + "http://localhost:8081/rest/getContext/" + prefix + "/" + no * 20 + "/20"
        }).then(function (data) {
            createCursore(no + 1);
            console.log(data);
            var airfoil_list = document.getElementById('airfoil_list');
            if (data.length === 0) {
                airfoil_list.innerHTML = "не удалось загрузить airfoil с перфиксом " + prefix;
            } else {
                data.forEach(logArrayElements);
            }

            function logArrayElements(element, index, array) {

                // <div class="col-sm-6 col-lg-6 col-md-6">
                //    <div class="thumbnail">
                //       <img src="img/a18-il.png" alt="">
                //       <div class="caption">
                //          <h4><a href="#">(a18-il) A18 (original)</a>
                //          </h4>
                //          <p>Drela AG26 airfoil used on the Bubble Dancer R/C DLG<br>Max thickness 6.8% at 23.3% chord<br>Max camber 2.5% at 45.5% chord<br>Source <a rel="nofollow" target="_blank" onclick="_gaq.push(['_trackEvent','external','http://www.ae.illinois.edu/m-selig/ads/coord_database.html', '']);return true;" href="/site/external?url=http%3A%2F%2Fwww.ae.illinois.edu%2Fm-selig%2Fads%2Fcoord_database.html">UIUC Airfoil Coordinates Database</a></p>
                //       </div>
                //    </div>
                // </div>


                var div = document.createElement('div');
                div.setAttribute('class', "col-sm-6 col-lg-6 col-md-6");
                var thumbnail = document.createElement('div');
                thumbnail.setAttribute('class', "thumbnail");
                div.appendChild(thumbnail);

                var image = document.createElement('img');
                image.setAttribute('src', element.image);
                thumbnail.appendChild(image);

                var caption = document.createElement('div');
                caption.setAttribute('class', "caption");

                var h4 = document.createElement('h4');
                var name_detail_info_link = document.createElement('a');
                name_detail_info_link.setAttribute('href', 'detailInfo.html?airfoilId=' + element.shortName);
                name_detail_info_link.innerText = element.shortName;
                h4.appendChild(name_detail_info_link);
                caption.appendChild(h4);
                var description = document.createElement('div');
                description.innerText = element.name + ':</br>' + element.description;
                caption.appendChild(description);
                thumbnail.appendChild(caption);

                airfoil_list.appendChild(div);
            }
        });
    });
}


// <ul class="pagination">
//    <li class="disabled"><a href="#">&laquo;</a></li>
//    <li class="active"><a href="#">1 <span class="sr-only">(current)</span></a></li>
//    <li class=""><a href="#">2 <span class="sr-only">(current)</span></a></li>
//    <li class=""><a href="#">3 <span class="sr-only">(current)</span></a></li>
//    <li class=""><a href="#">4 <span class="sr-only">(current)</span></a></li>
//    <li class=""><a href="#">5 <span class="sr-only">(current)</span></a></li>
//    <li class=""><a href="#" aria-label="Next"><span aria-hidden="true">»</span></a></li>
// </ul>

function createCursore(no) {
    console.log("createCursore");
    // var prefix = $.getUrlVar('prefix') != undefined ? $.getUrlVar('prefix') : 'A';
    var prefix = 'A';
    $(document).ready(function () {
        $.ajax({
            url: rootUrl + "http://localhost:8081/rest/getCountAirfoil/" + prefix
        }).then(function (data) {
            console.log(data);
            var countItem = Math.ceil(data / 20.0);
            console.log("countItem" + countItem);
            var cursor = document.getElementById('cursor');

            var item = document.createElement('li');
            var link = document.createElement('a');

            link.innerText = "«";

            if (no == 1) {
                item.setAttribute("class", "disabled");
            } else {
                var no_last = no - 1;
                link.setAttribute("href", "airfoilList.html?prefix=" + prefix + '&no=' + no_last);
            }
            item.appendChild(link);
            cursor.appendChild(item);

            for (var i = 1; i <= countItem; i++) {
                console.log(i);
                item = document.createElement('li');
                item.setAttribute("class", "");

                link = document.createElement('a');
                link.setAttribute("href", "airfoilList.html?prefix=" + prefix + '&no=' + i);

                if (no == i) {
                    item.setAttribute("class", "active");
                }
                link.innerHTML = i + ' <span class="sr-only">(current)</span>';

                item.appendChild(link);
                cursor.appendChild(item);
            }

            item = document.createElement('li');
            link = document.createElement('a');

            link.innerHTML = '<span aria-hidden="true">»</span>';
            link.setAttribute('aria-label', "Next");
            if (no == countItem) {
                item.setAttribute("class", "disabled");
            } else {
                var no_next = no + 1;
                link.setAttribute("href", "airfoilList.html?prefix=" + prefix + '&no=' + no_next);
            }
            item.appendChild(link);
            cursor.appendChild(item);
            // document.getElementById('contentid').appendChild(cursor);
        });
    });


}