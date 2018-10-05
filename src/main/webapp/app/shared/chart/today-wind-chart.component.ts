import {Component, OnDestroy, OnInit} from '@angular/core';
import * as Highcharts from 'highcharts';

@Component({
    selector: 'jhi-today-wind-chart',
    template: `
        <div>
            <highcharts-chart
                [Highcharts]="Highcharts"
                [options]="chartOptions"

                style="width: 50%; height: 400px; display: block;">

            </highcharts-chart>
        </div>`
})
export class TodayWindChartComponent implements OnInit, OnDestroy {
    Highcharts = Highcharts; // required
    chartConstructor = 'chart'; // optional string, defaults to 'chart'
    chartOptions = {
        series: [{
            data: [1, 2, 3]
        }],
        title: {
            text: 'a good title'
        }
    };

    constructor() {
    }

    ngOnInit() {
        console.log('loaded todaywindchart');
    }

    setClasses(alert) {
    }

    ngOnDestroy() {
    }
}
