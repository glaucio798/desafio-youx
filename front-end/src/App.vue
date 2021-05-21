<template>
  <div id="app">
    <b-row no-gutters id="nav">
      <b-col>
        <router-link class="mr-1 ml-auto" to="/">CADASTRO</router-link></b-col
      >
      <b-col> <router-link class="ml-1" to="/mapa">MAPA</router-link></b-col>
    </b-row>
    <router-view />
  </div>
</template>

<style lang="scss">
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2d3142;
  background-color: #d8d5db;
  min-height: 100vh;
}

#nav {
  padding: 30px;

  .col {
    text-align: end;

    &:nth-child(2) {
      text-align: start;
    }
  }
}

#nav a {
  font-weight: bold;
  color: #987284;

  &:hover {
    text-decoration: none;
    border-bottom: 2px solid #368f8b;
  }
}

#nav a.router-link-exact-active {
  color: #000000;
  border-bottom: 2px solid #368f8b;
}
</style>
