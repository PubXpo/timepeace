    /*sidebar nav collapse*/
    $(document).ready(function() {
        $('.parent').on("click",function(){
        $(this).find(".sub-nav").toggle();
        $(this).siblings().find(".sub-nav").hide();
        if($(".sub-nav:visible").length === 0 ){
            $("#menu-overlay").hide();
        }else {
        $("#menu-overlay").show();
        }
        });
        $("#menu-overlay").on("click",function(){
        $(".sub-nav").hide();
        $(this).hide();
        });
   });

function checkStatus() {
    let stat = document.querySelector('#week_state span').innerHTML;
    let element = document.querySelector('#lower_main_section');
    let buttons = document.querySelector('.bi').innerHTML;
    if(stat == "submitted"){
      console.log("submitted");
      element.style.pointerEvents = "none";
    }
 }
 var colorTheme = ['#067421','#00A0FF', '#9F9F9F']


/*high chart config JS*/
   $(function() {
        Highcharts.chart('dashContainer', {
          chart: {
            type: 'column'
          },
          title: {
            text: 'Trend of Earnings: '+theYear +' v '+ (theYear - 1)
          },
          subtitle: {
            text: 'Source: Contractor Data'
          },

          xAxis: {
            categories: mapCategories,
            crosshair: true
          },
          yAxis: {
            min: 0,
            title: {
              text: 'Earnings (£)'
            }
          },
          tooltip: {
            headerFormat: '<span style="font-size:16px">{point.key}</span><table>',
            pointFormat: '<tr><td style="padding:0">{series.name}: </td>' +
              '<td style="padding:0"><b>{point.y:.0f}</b></td></tr>',
            footerFormat: '</table>',
            shared: true,
            useHTML: true
          },
          plotOptions: {
            column: {
              pointPadding: 0.2,
              borderWidth: 0
            }
          },
              colors: colorTheme,
              series: [{
              name: theYear,
              colorByPoint: true,
              data: incomeMap,

            },
            {
              name: (theYear - 1),
              colorByPoint: true,
              data: incomeMapPrevYear,
            }
            ]
        });
})

/*high chart config JS - Avg Hours Worked*/
   $(function() {
        Highcharts.chart('AvgContainer', {
          chart: {
            type: 'spline'
          },
          title: {
            text: 'Trend of Days:'
          },
          subtitle: {
            text: 'Source: Contractor Data'
          },
          xAxis: {
//            categories: mapCategories,
            categories: ['a','b','c'],
            crosshair: true
          },
          yAxis: {
            min: 0,
            title: {
              text: 'Hours'
            }
          },
          tooltip: {
            headerFormat: '<span style="font-size:16px">{point.key}</span><table>',
            pointFormat: '<tr><td style="padding:0">{series.name}: </td>' +
              '<td style="padding:0"><b>{point.y:.0f}</b></td></tr>',
            footerFormat: '</table>',
            shared: true,
            useHTML: true
          },
          plotOptions: {
            column: {
              pointPadding: 0.2,
              borderWidth: 0
            }
          },

           series: [{
              name: theYear,
              colorByPoint: true,
              data: [1,2,3]
//              data: avgHoursMap

            }]
        });
}) ;



 $(document).ready(function() {
            var title = {
            text: 'Trend of Average Hours Worked Each Day'
            };
            var subtitle = {
            text: 'Source: Contractor Data'
            };
            var xAxis = {
            categories: mapCategories
            };
            var yAxis = {
            title: {
            text: 'Hours'
            },
            plotLines: [{
            value: 0,
            width: 3,
            color: '#808080'
            }]
            };
            var tooltip = {
            valueSuffix: ' hrs'
            }
            var legend = {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle',
            borderWidth: 0
            };
            var series =  [{
            name: 'Averages',
            data: avgHoursMap
            }
            ];

            var json = {};
            json.title = title;
            json.subtitle = subtitle;
            json.xAxis = xAxis;
            json.yAxis = yAxis;
            json.tooltip = tooltip;
            json.legend = legend;
            json.series = series;

            $('#AvgContainer').highcharts(json);
            });




$(document).ready( function () {
    $('#activeWeeksTable').DataTable();
    $('#activeWeeksTable_length').hide();
    $('#activeWeeksTable_filter').hide();
    $('#activeWeeksTable_info').hide();
    $('#activeWeeksTable_paginate').hide();

} );

$(document).ready( function () {
    $('#pastview').DataTable();
    $('#pastview_length').hide();
    $('#pastview_filter').hide();
    $('#pastview_info').hide();
    $('#pastview_paginate').hide();

} );

$(document).ready( function () {
    $('#allProjectsTable').DataTable();
} );


