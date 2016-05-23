package br.com.fiap.app;

import twitter4j.Query;

public class Main {

	public static void main(String[] args) {
		try {
			Query query = new Query("#java8");
			query.setSince("2016-05-15");
			query.setUntil("2016-05-22");
			System.out.println("Executando...");
			new FiltraTwitter(query).imprimeQtdTweetsPorDia()
				.imprimeQtdRetweetsPorDia().imprimeQtdFavoritosPorDia()
				.imprimeDataMaisRecente().imprimeDataMenosRecente()
				.imprimePrimeiroUsuarioDaLista().imprimeUltimoUsuarioDaLista().sendTwitter();
			System.out.println("Executado com sucesso");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}