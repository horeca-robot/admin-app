import { createRouter, createWebHistory } from "vue-router"

// Import Pages
import LoginPage from '../pages/LoginPage'
import EmployeesPage from '../pages/EmployeesPage'
import MenuPage from '../pages/MenuPage'
import RobotsPage from '../pages/RobotsPage'
import MapPage from '../pages/MapPage'
import SettingsPage from '../pages/SettingsPage'
import ApiExamplePage from '../pages/ApiExamplePage'

//Define Routes
const routes = [
    {
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
        path: '/settings',
        name: 'settings',
        component: SettingsPage,
        meta: {
            requiresAuth: true
        }
    },
    {
      path: '/testpage',
      name: 'testpage',
      component: ApiExamplePage,
      meta: {
          requiresAuth: false
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
    //Redirect to login
    if(to.path === '/'){
        localStorage.setItem('jwt', 'test')
        next({ name: 'login' })
    }   

    //AuthenticationState
    if (to.matched.some(record => record.meta.requiresAuth)) {
      if (localStorage.getItem('jwt') == null) {
        next({
          name: 'login',
          params: { nextUrl: to.fullPath }
        })
      } 
      else {
        next()
      }
    } 
    else if (to.matched.some(record => record.meta.guest)) {
      if (localStorage.getItem('jwt') == null) {
        next()
      } 
      else {
        next({ name: 'employees' })
      }
    } 
    else {
      next()
    }
})

export default router