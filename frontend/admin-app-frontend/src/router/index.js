import { createRouter, createWebHistory } from "vue-router"

// Import Pages
import LoginPage from '../pages/LoginPage'
import EmployeesPage from '../pages/EmployeesPage'
import MenuPage from '../pages/MenuPage'
import ProductPage from '../pages/ProductPage'
import CategoryPage from '../pages/CategoryPage'
import RobotsPage from '../pages/RobotsPage'
import MapPage from '../pages/MapPage'
import OrderHistoryPage from '../pages/OrderHistoryPage'
import SettingsPage from '../pages/SettingsPage'
import ForgotPasswordPage from '../pages/ForgotPasswordPage'
import JwtUtil from "../utils/JwtUtil"

//Define Routes
const routes = [{
        path: '/login',
        name: 'login',
        component: LoginPage,
        meta: {
            guest: true
        }
    },
    {
        path: '/employees',
        name: 'employees',
        component: EmployeesPage,
        meta: {
            requiresAuth: true
        }
    },
    {
        path: '/menu',
        name: 'menu',
        component: MenuPage,
        meta: {
            requiresAuth: true
        }
    },
    {
        path: '/product',
        name: 'product',
        component: ProductPage,
        meta: {
            requiresAuth: true
        }
    },
    {
        path: '/category',
        name: 'category',
        component: CategoryPage,
        meta: {
            requiresAuth: true
        }
    },
    {
        path: '/robots',
        name: 'robots',
        component: RobotsPage,
        meta: {
            requiresAuth: true
        }
    },
    {
        path: '/map',
        name: 'map',
        component: MapPage,
        meta: {
            requiresAuth: true
        }
    },
    {
        path: '/order-history',
        name: 'order-history',
        component: OrderHistoryPage,
        meta: {
            requiresAuth: true
        }
    },
    {
        path: '/settings',
        name: 'settings',
        component: SettingsPage,
        meta: {
            requiresAuth: true
        }
    },
    {
        path: '/forgot-password',
        name: 'forgot-password',
        component: ForgotPasswordPage,
        meta: {
            guest: true
        }
    }
]

//Create Router
const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

//Check before entering page
router.beforeEach((to, from, next) => {
    //Redirect when trying to access home page
    if (to.path === '/') {
        if (localStorage.getItem('jwt') == null) {
            next({
                name: 'login',
                params: { nextUrl: to.fullPath }
            })
        } else {
            next({ name: 'employees' })
        }
    }

    //AuthenticationState
    if (to.matched.some(record => record.meta.requiresAuth)) {
        if (localStorage.getItem('jwt') == null) {
            next({
                name: 'login',
                params: { nextUrl: to.fullPath }
            })
        } else {
            const claims = JwtUtil.parseJwt(localStorage.getItem("jwt"))
            const isExpired = JwtUtil.checkExpiration(claims["exp"])

            if (isExpired) {
                localStorage.removeItem("jwt")

                next({
                    name: 'login',
                    params: { nextUrl: to.fullPath }
                })
            } else {
                next()
            }
        }
    } else if (to.matched.some(record => record.meta.guest)) {
        if (localStorage.getItem('jwt') == null) {
            next()
        } else {
            next({ name: 'employees' })
        }
    } else {
        next()
    }
})
export default router