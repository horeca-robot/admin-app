import { notify } from 'notiwind'

export default {
    showSuccessNotification(message){
        notify({
            group: "success",
            text: message
        }, 3000)
    },
    showErrorNotification(message){
        notify({
            group: "error",
            text: message
        }, 3000)
    },
    showInfoNotification(message){
        notify({
            group: "info",
            text: message
        }, 3000)
    },
    showConfirmNotification(message){
        notify({
            group: "confirm",
            text: message
        }, 3000)
    }
}