package portfolio.data;

public class PortfolioDAOFactory {
   
   public static PortfolioData getPortfolioData() {   
      return PortfolioNoPersistenceData.getInstance();
      //return new PortfolioDummyData();
      //return new PortfolioHsqldbData();
   }
}
