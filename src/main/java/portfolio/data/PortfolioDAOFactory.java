package portfolio.data;

public class PortfolioDAOFactory {
   
   public static PortfolioData getPortfolioData() {   
      return new PortfolioNoPersistenceData();
      //return new PortfolioDummyData();
      //return new PortfolioHsqldbData();
   }
}
