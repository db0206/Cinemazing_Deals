import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import AdminPage from './Pages/AdminPage';
import LoginPage from './Pages/LoginPage';
import CompanyPage from './Pages/CompanyPage';
import CustomerPage from './Pages/CustomerPage';
import NotFoundPage from './Pages/NotFoundPage';



function App() {
  return (
    <Router>
      <Routes>
        <Route path='/' element={<LoginPage/>}/>
        <Route path='/admin' element={<AdminPage/>}/>
        <Route path='/company' element={<CompanyPage/>}/>
        <Route path='/customer' element={<CustomerPage/>}/>
        <Route path='*' element={<NotFoundPage/>}/>
      </Routes>
    </Router>
  );
}

export default App;
