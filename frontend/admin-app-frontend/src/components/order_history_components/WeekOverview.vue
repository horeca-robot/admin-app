<template>
    <div id="overview">
        <div v-if="selectedDay.name" id="info">
            <h1>{{ selectedDay.name }}</h1>
            <div>
                <label>Average Total Orders:<label>{{ selectedDay.amount ? (selectedDay.orders / selectedDay.amount) : 0 }}</label></label>
                <label>Average Total Revenue:<label>€{{ selectedDay.amount ? (selectedDay.subTotal / selectedDay.amount).toFixed(2) : 0 }}</label></label>
            </div>
        </div>
        <div v-else id="info">
            <h1 id="message">Hover over a day.</h1>
        </div>
        <div id="days">
            <DayColumn v-for="day in week" :key="day.name" :day="day" :highest="highestAmount" @onHover="selectDay"/>
        </div>
    </div>
</template>
<script>
import DayColumn from './DayColumn.vue'

export default {
    data() {
        return {
            week: [
                { id: 1, name: 'Monday', orders: 0, subTotal: 0.00, amount: 0 },
                { id: 2, name: 'Tuesday', orders: 0, subTotal: 0.00, amount: 0 },
                { id: 3, name: 'Wednesday', orders: 0, subTotal: 0.00, amount: 0 },
                { id: 4, name: 'Thursday', orders: 0, subTotal: 0.00, amount: 0 },
                { id: 5, name: 'Friday', orders: 0, subTotal: 0.00, amount: 0 },
                { id: 6, name: 'Saturday', orders: 0, subTotal: 0.00, amount: 0 },
                { id: 0, name: 'Sunday', orders: 0, subTotal: 0.00, amount: 0 }
            ],
            selectedDay: {},
            highestAmount: 0
        }
    },
    props: {
        orders: Array
    },
    components: {
        DayColumn
    },
    watch: {
        orders() {
            this.fillWeekData()
        },
    },
    created() {
        this.fillWeekData()
    },
    methods: {
        resetValues(){
            this.week = [
                { id: 1, name: 'Monday', orders: 0, subTotal: 0.00, amount: 0 },
                { id: 2, name: 'Tuesday', orders: 0, subTotal: 0.00, amount: 0 },
                { id: 3, name: 'Wednesday', orders: 0, subTotal: 0.00, amount: 0 },
                { id: 4, name: 'Thursday', orders: 0, subTotal: 0.00, amount: 0 },
                { id: 5, name: 'Friday', orders: 0, subTotal: 0.00, amount: 0 },
                { id: 6, name: 'Saturday', orders: 0, subTotal: 0.00, amount: 0 },
                { id: 0, name: 'Sunday', orders: 0, subTotal: 0.00, amount: 0 }
            ]
            this.selectedDay = {}
            this.highestAmount = 0
        },
        fillWeekData(){
            this.resetValues()
            console.log(this.orders)
            this.orders.forEach(o => o.createdAt = new Date(o.createdAt))
            this.week.forEach(day => {
                this.orders.filter(o => o.createdAt.getDay() === day.id).forEach(o => {
                    day.orders++
                    day.subTotal = day.subTotal + o.subTotal
                })

                const days = this.orders.filter(o => o.createdAt.getDay() === day.id)
                .map(o => `${String(o.createdAt.getDate()).padStart(2, '0')}-${String(o.createdAt.getMonth() + 1).padStart(2, '0')}-${o.createdAt.getFullYear()}`)
                day.amount = days.filter(this.onlyUnique).length
            })
            this.getHighestOrderAmount()
            console.log(this.week)
        },
        onlyUnique(value, index, self) {
            return self.indexOf(value) === index;
        },
        getHighestOrderAmount(){
            this.highestAmount = this.week.map(d => d.amount ? (d.orders / d.amount) : 0).sort((a,b) => (a < b) ? 1 : ((b < a) ? -1 : 0))[0]
        },
        selectDay(day){
            this.selectedDay = day
        }
    }
}
</script>
<style scoped>
    #overview{
        display: flex;
        flex-direction: column;
        width: 100%;
        height: 95%;
        justify-content: space-between;
    }

    #info{
        height: 25%;
        width: calc(100% - 24px);
        border: 2px solid var(--primary-color);
        border-radius: 5px;
        padding: 10px;
        display: flex;
        flex-direction: column;
        justify-content: space-between;
    }

        #info h1{
            margin: 0;
            color: var(--primary-color);
            font-weight: normal;
        }

        #info div{
            display: flex;
            flex-direction: column;
        }

        #info label{
            margin-top: 5px;
            color: var(--text-color);
            font-size: 1.5em;
        }

            #info label label{
                color: var(--primary-color);
                font-size: 1em;
                margin-left: 10px;
            }

    #message{
        width: 100%;
        height: 100%;
        display: flex;
        align-items: center;
        justify-content: center;
        color: var(--text-color) !important;
    }

    #days{
        display: flex;
        height: 75%;
        width: 100%;
        justify-content: space-between;
        align-items: flex-end;
    }
</style>