package br.com.fiap.app;

import twitter4j.Query;

public class Main {

	public static void main(String[] args) {
		try {
			Query query = new Query("#java8");
			query.setSince("2016-05-06");
			query.setUntil("2016-05-16");
			new FiltraTwitter(query).imprimeQtdTweetsPorDia()
				.imprimeQtdRetweetsPorDia().imprimeQtdFavoritosPorDia()
				.imprimeDataMaisRecente().imprimeDataMenosRecente()
				.imprimePrimeiroUsuarioDaLista().imprimeUltimoUsuarioDaLista();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}