import React from 'react';
import { Route, BrowserRouter as Router } from "react-router-dom";
import AddVisitor from "./Components/AddVisitor";
import ListVisitors from "./Components/ListVisitors";

import './App.css';

function App() {
  return (

    <Router>
        <Route exact path="/" component={AddVisitor} />
        <Route exact path="/visitors" component={ListVisitors} />
      </Router>

  );
}

export default App;
