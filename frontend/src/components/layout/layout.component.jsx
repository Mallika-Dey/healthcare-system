import Footer from "./footer.component";
import Navigation from "./navigate.component";

const Layout = ({ children }) => {
  return (
    <div>
      <Navigation />
      <div className="min-vh-100">{children}</div>
      <Footer />
    </div>
  );
};

export default Layout;
