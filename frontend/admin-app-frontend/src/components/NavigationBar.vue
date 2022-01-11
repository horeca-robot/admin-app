<template>
    <div class="navigation-bar">
        <div class="top-part">
            <div class="section">
                <h1 v-if="this.restaurantName !== undefined && this.restaurantName.length" class="restaurant-name">{{text.Nav_Welcome}}<br/>{{ this.restaurantName }}</h1>
                <h1 v-else class="restaurant-name">{{text.Nav_Welcome}}<br/>Administrator</h1>
            </div>
            <div class="section">
                <a class="link" :href="employeeApp">
                    {{text.Nav_Ref}}
                    <i class="link-icon fas fa-external-link-alt"/>
                </a>
                <a class="link" @click="handleLogOut">
                    {{text.Nav_Logout}}
                    <i class="link-icon fas fa-sign-out-alt"/>
                </a>
            </div>
        </div>
        <div class="bottom-part">
            <div class="section">
                <div :class="{'nav-item nav-item-selected': currentPage === 'employees',  'nav-item': currentPage !== 'employees'}" @click="redirectToPage('employees')">
                    <div class="nav-item-content">
                        <i class="nav-icon fas fa-users"/>
                        {{text.Nav_Employees}}
                    </div>
                </div>
                <div :class="{'nav-item nav-item-selected': currentPage === 'menu',  'nav-item': currentPage !== 'menu'}" @click="redirectToPage('menu')">
                    <div class="nav-item-content">
                        <i class="nav-icon fas fa-utensils"/>
                        {{text.Nav_Menu}}
                    </div>
                </div>
                <div :class="{'nav-item nav-item-selected': currentPage === 'robots',  'nav-item': currentPage !== 'robots'}" @click="redirectToPage('robots')">
                    <div class="nav-item-content">
                        <i class="nav-icon fas fa-robot"/>
                        {{text.Nav_Robots}}
                    </div>
                </div>
                <div :class="{'nav-item nav-item-selected': currentPage === 'map',  'nav-item': currentPage !== 'map'}" @click="redirectToPage('map')">
                    <div class="nav-item-content">
                        <i class="nav-icon fas fa-map-marked-alt"/>
                        {{text.Nav_Map}}
                    </div>
                </div>
                <div :class="{'nav-item nav-item-selected': currentPage === 'order-history',  'nav-item': currentPage !== 'order-history'}" @click="redirectToPage('order-history')">
                    <div class="nav-item-content">
                        <i class="nav-icon fas fa-history"/>
                        {{text.Nav_OrderHistory}}
                    </div>
                </div>
            </div>
            <div class="section">
                <div :class="{'nav-item nav-item-selected': currentPage === 'settings',  'nav-item': currentPage !== 'settings'}" @click="redirectToPage('settings')">
                    <div class="nav-item-content">
                        <i class="nav-icon fas fa-cog"/>
                        {{text.Nav_Settings}}
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import LanguageUtil from '../utils/LanguageUtil'

export default {
    name: 'NavigationBar',
    data(){
        return{
            text: LanguageUtil.getTextObject(),
            employeeApp: process.env.VUE_APP_EMPLOYEE_APP_URL
        }
    },
    props: {
        currentPage: String,
        restaurantName: String
    },
    created() {
        this.getRestaurantName()
    },
    methods: {
        getRestaurantName() {
            //TODO: Add code to get restaurant name
        },

        handleLogOut(e) {
            e.preventDefault()

            localStorage.removeItem('jwt')
            this.$router.push('login')
        },

        redirectToPage(route){
            this.$router.push(route)
        }
    }
}
</script>

<style scoped>
    .navigation-bar{
        position: absolute;
        background: var(--secondary-color);
        box-shadow: 5px 4px 5px 4px rgb(0 0 0 / 50%);
        height: 100%;
        width: 12.5vw;
    }

    .top-part{
        height: 15%;
        padding: 10%;
        border-bottom: 1px solid var(--text-color);
        display: flex;
        flex-direction: column;
        justify-content: space-between;
    }

    .bottom-part{
        height: 80%;
        display: flex;
        flex-direction: column;
        justify-content: space-between;
    }

    .section{
        display: flex;
        flex-direction: column;
    }

    .restaurant-name{
        margin: 0;
        font-size: 1.75rem;
        color: var(--text-color);
    }

    .link{
        font-size: 1.2rem;
        color: var(--primary-color);
        text-decoration: none;
        margin-top: 7.5%;
    }

    .link:hover{
        cursor: pointer;
    }

    .link-icon{
        margin-left: 2.5%;   
    }

    .nav-item{
        margin-top: 15px;
        background: var(--primary-color);
        opacity: 0.75;
        box-shadow: 0px 4px 4px rgb(0 0 0 / 25%);
        display: flex;
        justify-content: center;
    }

    .nav-item:hover{
        cursor: pointer;
        opacity: 1 !important;
    }

    .nav-item-selected{
        opacity: 1 !important;
    }

    .nav-item-content{
        width: 80%;
        color: var(--secondary-color);
        font-size: 1.25rem;
        padding: 12.5px;
        display: flex;
        align-items: center;
    }

    .nav-icon{
        min-width: 25px;
        margin-right: 12.5px;
        font-size: 1.2em;
        text-align: center;
    }
</style>