import {Component, OnDestroy, OnInit} from '@angular/core';
import * as Highcharts from 'highcharts';
import {MeasurementService} from "../../entities/measurement";
import {IMeasurement} from "../model/measurement.model";
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {ISurfSpot} from "../model/surf-spot.model";
import {JhiAlertService} from "ng-jhipster";
import {Input} from '@angular/core';

require('highcharts/modules/data')(Highcharts);
require('highcharts/modules/exporting')(Highcharts);
require('highcharts/modules/windbarb')(Highcharts);
require('highcharts/highcharts-more')(Highcharts);

@Component({
    selector: 'jhi-today-wind-chart',
    template: `
        <div>
            <highcharts-chart
                [Highcharts]="Highcharts"
                [options]="chartOptions"
                style="
                min-width: 300px;
                max-width: 800px;
                height: 300px;
                margin: 1em auto;">
            </highcharts-chart>
        </div>`
})
export class TodayWindChartComponent implements OnInit, OnDestroy {
    Highcharts = Highcharts; // required
    chartConstructor = 'chart'; // optional string, defaults to 'chart'
    @Input() chartData: any[];

    chartOptions = {};

    constructor(private jhiAlertService: JhiAlertService) {
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    ngOnInit() {
        console.log('loaded todaywindchart');
        console.log("chartDatas" + JSON.stringify(this.chartData));
        this.setChartOptions();
    }

    setChartOptions() {
        this.chartOptions = {
            title: {
                text: 'Verket'
            },

            xAxis: {
                type: 'datetime',
                offset: 40
            },

            plotOptions: {
                series: {
                    pointStart: Date.UTC(2017, 0, 29),
                    pointInterval: 36e5
                }
            },

            series: [{
                type: 'windbarb',
                data: this.chartData,
                showInLegend: false,
                tooltip: {
                    valueSuffix: ' m/s'
                }
            }, {
                type: 'area',
                keys: ['y', 'rotation'], // rotation is not used here
                data: this.chartData,
                color: Highcharts.getOptions().colors[0],
                fillColor: {
                    linearGradient: {x1: 0, x2: 0, y1: 0, y2: 1},
                    stops: [
                        [0, Highcharts.getOptions().colors[0]],
                        [
                            1,
                            Highcharts.color(Highcharts.getOptions().colors[0])
                                .setOpacity(0.25).get()
                        ]
                    ]
                },
                name: 'Wind speed',
                tooltip: {
                    valueSuffix: ' m/s'
                }
            }]
        };
    }

    setClasses(alert) {
    }

    ngOnDestroy() {
    }
}
