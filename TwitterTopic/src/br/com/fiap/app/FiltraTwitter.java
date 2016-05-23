package br.com.fiap.app;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.TwitterException;

public class FiltraTwitter {
	
	Map<LocalDate, Set<TwitterVo>> map;
	
	public FiltraTwitter(Query query) throws TwitterException {
		filtraTwitts(query);
	}

	public void filtraTwitts(Query query) throws TwitterException {
		ClientFactory client = new ClientFactory();
		QueryResult result = client.getConn().search(query);
		Set<TwitterVo> lista = new HashSet<>();
		while (result.hasNext()) {
			query = result.nextQuery();
			lista.addAll(result.getTweets().stream().map(twitt -> new TwitterVo(twitt)).collect(Collectors.toSet()));
			result = client.getConn().search(query);
		}
		map = lista.stream()
		.collect(Collectors.groupingBy(TwitterVo::getCreatedAt, Collectors.mapping(Function.identity(), Collectors.toSet())));
	}
	
	public void sendTwitter() throws TwitterException{
		ClientFactory client = new ClientFactory();
		client.getConn().updateStatus("Prof. Michel @michelpf da turma 27SCJ trabalho concluido");
	}
	
	public FiltraTwitter imprimeQtdTweetsPorDia(){
		System.out.println("====== TWEETS POR DIA ======");
		map.forEach((data,list) -> System.out.println(data+" "+list.size()+" Tweets"));
		return this;
	}
	
	public FiltraTwitter imprimeQtdRetweetsPorDia(){
		System.out.println("====== RETWEETS POR DIA ======");
		map.forEach((data,lista) -> System.out.println(data+" "+lista.stream().mapToInt(vo -> vo.getRetweetCount()).sum()+" Retweets"));
		return this;
	}
	
	public FiltraTwitter imprimeQtdFavoritosPorDia(){
		System.out.println("====== FAVORITOS POR DIA ======");
		map.forEach((data,lista) -> System.out.println(data+" "+lista.stream().mapToInt(vo -> vo.getFavoriteCount()).sum()+" Favorites"));
		return this;
	}
	
	public FiltraTwitter imprimeDataMaisRecente(){
		System.out.println("====== DATA MAIS RECENTE ======");
		System.out.println(map.keySet().stream().sorted((d1,d2) -> d2.compareTo(d1)).findFirst().get());
		return this;
	}
	
	public FiltraTwitter imprimeDataMenosRecente(){
		System.out.println("====== DATA MENOS RECENTE ======");
		System.out.println(map.keySet().stream().sorted((d1,d2) -> d1.compareTo(d2)).findFirst().get());
		return this;
	}
	
	public FiltraTwitter imprimePrimeiroUsuarioDaLista(){
		System.out.println("====== PRIMEIRO USUARIO DA LISTA ======");
		TwitterVo twitterVo = listaTodosTwitts().stream().sorted((t1,t2) -> t1.getUser().getName().compareTo(t2.getUser().getName())).findFirst().get();
		System.out.println(twitterVo.getUser().getName());
		return this;
	}
	
	public FiltraTwitter imprimeUltimoUsuarioDaLista(){
		System.out.println("====== ULTIMO USUARIO DA LISTA ======");
		TwitterVo twitterVo2 = listaTodosTwitts().stream().sorted((t1,t2) -> t2.getUser().getName().compareTo(t1.getUser().getName())).findFirst().get();
		System.out.println(twitterVo2.getUser().getName());
		return this;
	}

	private Set<TwitterVo> listaTodosTwitts(){
		Set<TwitterVo> listaTotal = new HashSet<>();
		map.forEach((data,lista) -> {listaTotal.addAll(lista);});
		return listaTotal;
	}
	
}