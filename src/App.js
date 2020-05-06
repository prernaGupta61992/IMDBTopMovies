import React, { Component } from 'react'
import Main from './Main'
import Background from './images/topmovies.jpg'


var sectionStyle = {
  width: "100%",
  height: "400px",
  backgroundImage: `url(${Background})`
};

class App extends Component {

  render() {

    return (
      <section style={ sectionStyle }>
        <Main />
      </section>
    );
  }
  
}


export default App;