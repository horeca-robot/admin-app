<template>
    <div id="page">
        <div class="panel">
            <div id="time-section">
                <div id="time-options">
                    <label @click="setDatesByPreviousDaysAmount(7)">Last 7 days</label>
                    <label @click="setDatesByPreviousDaysAmount(30)">Last 30 days</label>
                    <label @click="setDatesByPreviousDaysAmount(90)">Last 90 days</label>
                    <label @click="setDatesByPreviousDaysAmount(180)">Last 180 days</label>
                    <label @click="setDatesByPreviousDaysAmount(365)">Last 365 days</label>
                    <label @click="setDefaultDateValues()">All time</label>
                </div>
                <div id="time-select">
                    <div>
                        <label>From:</label>
                        <input type="date" :value="dateToYYYYMMDD(this.fromTemp)" @input="fromTemp = $event.target.valueAsDate" :max="dateToYYYYMMDD(this.toTemp)"/>
                    </div>
                    <div>
                        <label>To:</label>
                        <input type="date" :value="dateToYYYYMMDD(this.toTemp)" @input="toTemp = $event.target.valueAsDate" :min="dateToYYYYMMDD(this.fromTemp)" :max="dateToYYYYMMDD(new Date())"/>
                    </div>
                </div>
                <button id="time-apply" @click="applyDates">Apply</button>
            </div>
            <div id="data-section">
                <label>Total Revenue: <label>€{{ getSumOfArray(this.orders.map(i => i.subTotal)) }}</label></label>
                <label>Total Orders: <label>{{ this.orders.length }}</label></label>
                <label>Average Order Revenue: <label>€{{ this.orders.length ? parseFloat(getSumOfArray(this.orders.map(i => i.subTotal)) / this.orders.length).toFixed(2) : 0 }}</label></label>
                <label>Total Products Ordered: <label>{{ this.products.length }}</label></label>
            </div>
            <label id="export">Export Order History</label>
        </div>
        <div class="panel">
            <WeekOverview :orders="orders"/>
        </div>
        <div v-if="categories.length" class="panel">
            <div id="categories">
                <CategoryDropdown v-for="category in categories.filter(i => i.products.length)" :key="category.rerender" 
                    :category="category" :orderedProducts="products.filter(i => i.categories.some(c => c === category.id))"/>
            </div>
        </div>
    </div>
</template>
<script>
import OrderWrapper from '../wrappers/OrderWrapper'
import CategoryWrapper from '../wrappers/CategoryWrapper'
import NotificationUtil from '../utils/NotificationUtil'
import WeekOverview from '../components/order_history_components/WeekOverview.vue'
import CategoryDropdown from '../components/order_history_components/CategoryDropdown.vue'

export default {
    data() {
        return {
            from: String,
            fromTemp: Date,
            to: String,
            toTemp: Date,
            orders: [],
            categories: [],
            products: []
        }
    },
    components: {
        WeekOverview,
        CategoryDropdown
    },
    async created() {
        this.setDefaultDateValues()
        await this.getCategories()
        await this.getOrderHistory()
    },
    methods: {
        setDefaultDateValues(){
            this.fromTemp = new Date('0001-01-01T00:00:00Z')
            this.from = this.dateToYYYYMMDD(this.fromTemp)
            this.toTemp = new Date()
            this.to = this.dateToYYYYMMDD(this.toTemp)
        },
        async getCategories(){
            const response = await CategoryWrapper.getCategories()

            if(response){
                this.categories = response.categories
                this.categories.forEach(c => c.rerender = false)
            }
            else{
                NotificationUtil.showErrorNotification(response.message)
            }
        },
        async getOrderHistory(){
            const response = await OrderWrapper.getOrderHistory()

            if(response){
                this.orders = response.orders
                this.getProducts()
                this.rerenderCategories()
            }
            else{
                NotificationUtil.showErrorNotification(response.message)
            }
        },
        async getOrderHistoryByDates(){
            const response = await OrderWrapper.getOrderHistoryByDates(this.from, this.to)

            if(response){
                this.orders = response.orders
                this.getProducts()
                this.rerenderCategories()
            }
            else{
                NotificationUtil.showErrorNotification(response.message)
            }
        },
        getProducts(){
            this.products = []
            const orderProducts = this.orders.map(i => i.products)
            orderProducts.forEach(list => list.forEach(p => this.products.push(p)))
        },
        setDatesByPreviousDaysAmount(d){
            this.fromTemp = new Date(new Date().setDate(new Date().getDate() - d))
            this.from = this.dateToYYYYMMDD(this.fromTemp)
            this.toTemp = new Date()
            this.to = this.dateToYYYYMMDD(this.toTemp)
        },
        applyDates(){
            this.from = this.dateToYYYYMMDD(this.fromTemp)
            this.to = this.dateToYYYYMMDD(this.toTemp)
            this.getOrderHistoryByDates()
        },
        dateToYYYYMMDD(d) {
            return d && new Date(d.getTime()-(d.getTimezoneOffset()*60*1000)).toISOString().split('T')[0]
        },
        getSumOfArray(array){
            let sum = 0.0
            array.forEach(i => sum += i)
            return sum.toFixed(2)
        },
        rerenderCategories(){
            this.categories.forEach(c => c.rerender = !c.rerender)
        }
    }
}
</script>
<style scoped>
    #page{       
        top:0;
        bottom: 0;
        right: 0;
        margin: auto;
        height: fit-content;
        width: 87.5vw;
        display: flex;
        align-items: center;
        justify-content: space-evenly;
    }

    .panel{        
        width: 22.5%;
        height: 60vh;
        padding: 20px;
        background-color: var(--secondary-color);
        box-shadow: 5px 5px 5px 1px rgba(0, 0, 0, 0.5);
        border-radius: 10px;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: space-between;
        overflow-x: hidden;
        overflow-y: overlay;
    }

    #time-section{
        display: flex;
        flex-direction: column;
        align-items: center;
        border: 2px solid var(--primary-color);
        border-radius: 5px;
        padding: 20px;
        margin-bottom: 20px;
    }

    #time-options{
        display: flex;
        flex-wrap: wrap;
        width: 100%;
    }

        #time-options label{
            width: 50%;
            font-size: 1.5em;
            color: var(--primary-color);
            margin-bottom: 5px;
        }

            #time-options label:hover{
                cursor: pointer;
                opacity: 0.75;
            }

    #time-select{
        width: 100%;
        display: flex;
        flex-direction: column;
        margin-top: 20px;
    }

        #time-select div{
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 10px;
        }

        #time-select label{
            font-size: 1.5em;
            color: var(--text-color);
        }

        #time-select input{
            background-color: var(--primary-color);
            border: none;
            border-radius: 5px;
            padding: 5px;
            color: var(--secondary-color);
            font-size: 1.5em;
            font-family: inherit;
            box-shadow: 2px 2px 5px 0px rgb(0 0 0 / 50%);
            cursor: pointer;
            outline: none;
        }

            #time-select input::-webkit-calendar-picker-indicator {
                filter: invert(1)
            }

    #time-apply{
        width: 100%;
        background-color: var(--primary-color);
        border: none;
        border-radius: 5px;
        padding: 7.5px 0px;
        color: var(--secondary-color);
        font-size: 1.5em;
        font-family: inherit;
        margin-top: 20px;
        box-shadow: 2px 2px 5px 0px rgb(0 0 0 / 50%);
        cursor: pointer;
    }

    #data-section{
        display: flex;
        flex-direction: column;
        align-items: center;
        border: 2px solid var(--primary-color);
        border-radius: 5px;
        padding: 20px;
        width: calc(100% - 45px);
        margin-bottom: 20px;
    }

        #data-section label{
            width: 100%;
            font-size: 1.5em;
            color: var(--text-color);
            margin-bottom: 5px;
        }

            #data-section label label{
                font-size: 1em;
                color: var(--primary-color);
            }

    #export{
        font-size: 1.5em;
        color: var(--primary-color);
    }

        #export:hover{
            cursor: pointer;
            opacity: 0.75;
        }

    #categories{
        width: 100%;
        height: 100%;
    }
</style>