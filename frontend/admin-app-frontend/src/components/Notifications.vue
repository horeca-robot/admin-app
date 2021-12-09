<template>
<div class="group-container">
    <NotificationGroup group="success">
        <div>
            <Notification v-slot="{ notifications, close }">
                <div v-for="notification in notifications" :key="notification.id" class="notification-panel">
                    <i class="success-icon fas fa-check-circle"/>
                    <h1>{{ notification.text }}</h1>
                    <div class="notification-exit">
                        <i @click="close(notification.id)" class="notification-exit-icon fas fa-times"/>
                    </div>
                </div>
            </Notification>
        </div>
    </NotificationGroup>

    <NotificationGroup group="error">
        <div>
            <Notification v-slot="{ notifications, close }">
                <div v-for="notification in notifications" :key="notification.id" class="notification-panel">
                    <i class="error-icon fas fa-times-circle"/>
                    <h1>{{ notification.text }}</h1>
                    <div class="notification-exit">
                        <i @click="close(notification.id)" class="notification-exit-icon fas fa-times"/>
                    </div>
                </div>
            </Notification>
        </div>
    </NotificationGroup>

    <NotificationGroup group="info">
        <div>
            <Notification v-slot="{ notifications, close }">
                <div v-for="notification in notifications" :key="notification.id" class="notification-panel">
                    <i class="info-icon fas fa-exclamation-circle"/>
                    <h1>{{ notification.text }}</h1>
                    <div class="notification-exit">
                        <i @click="close(notification.id)" class="notification-exit-icon fas fa-times"/>
                    </div>
                </div>
            </Notification>
        </div>
    </NotificationGroup>

    <NotificationGroup group="confirm">
        <div>
            <Notification v-slot="{ notifications, close }">
                <div v-for="notification in notifications" :key="notification.id" class="notification-panel">
                    <i class="confirm-icon fas fa-question-circle"/>
                    <h1>{{ notification.text }}</h1>
                    <div class="notification-exit">
                        <i @click="close(notification.id)" class="notification-exit-icon fas fa-times"/>
                    </div>
                </div>
            </Notification>
        </div>
    </NotificationGroup>
</div>
</template>
<script>
export default {
    name: 'Notifications'
}
</script>
<style scoped>
.group-container{
    position: fixed;
    top: 10px;
    right: 10px;
    margin: auto;
}

.notification-panel{
  background-color: white;
  box-shadow: 0px 0px 10px 5px rgb(0 0 0 / 50%);
  border-radius: 10px;
  min-height: 75px;
  width: 400px;
  margin-top: 10px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 5px;
}

h1{
    margin: 0px 10px;
    font-size: 1.5em;
    font-weight: normal;
}

.notification-exit{
  height: 100%;
  display: flex;
}

.notification-exit-icon{
  color: grey;
  font-size: 1em;
  top: 5px;
  right: 5px;
}

    .notification-exit-icon:hover{
        cursor: pointer;
        color: red;
    }

.success-icon{
  color: green;
  font-size: 3.5em;
  margin-left: 10px;
}

.error-icon{
  color: red;
  font-size: 3.5em;
  margin-left: 10px;
}

.info-icon{
  color: dodgerblue;
  font-size: 3.5em;
  margin-left: 10px;
}

.confirm-icon{
  color: orange;
  font-size: 3.5em;
  margin-left: 10px;
}
</style>