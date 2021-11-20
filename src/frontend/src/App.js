import React, { Component } from "react";
import { Router, Switch, Route } from 'react-router-dom';

import PhoneNumbers from './components/PhoneNumbers';

import { history } from "./helpers/history";

class App extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    return(
      <Router history={history}>
        <Switch>
          <Route exact path="/" component={ PhoneNumbers }></Route>
        </Switch>
      </Router>
    );
  }
}

export default App;
