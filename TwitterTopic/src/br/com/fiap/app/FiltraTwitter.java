package br.com.fiap.app;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.TwitterException;

public class FiltraTwitter {
	
	private Map<LocalDate, Set<TwitterVo>> map;
	
	private Set<String> msgs = new LinkedHashSet<>();
	
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
		msgs.forEach(msg -> System.out.println(msg));
		
		System.out.println(client.getConn().updateStatus("Prof. Michel @michelpf iniciando processo de coletagem de dados."));
		msgs.forEach(msg-> {
			try {
				System.out.println(client.getConn().updateStatus("Prof. Michel @michelpf " +msg));
			} catch (Exception e) {
				System.out.println("erro ao enviar msg");
			}
		});
		System.out.println(client.getConn().updateStatus("Prof. Michel @michelpf enviado coleta com sucesso."));
		
	}
	
	public FiltraTwitter imprimeQtdTweetsPorDia(){
		msgs.add("====== TWEETS POR DIA ======");
		map.forEach((data,list) -> msgs.add(data+" "+list.size()+" Tweets"));
		return this;
	}
	
	public FiltraTwitter imprimeQtdRetweetsPorDia(){
		msgs.add("====== RETWEETS POR DIA ======");
		map.forEach((data,lista) ->  msgs.add(data+" "+lista.stream().mapToInt(vo -> vo.getRetweetCount()).sum()+" Retweets"));
		return this;
	}
	
	public FiltraTwitter imprimeQtdFavoritosPorDia(){
		msgs.add("====== FAVORITOS POR DIA ======");
		map.forEach((data,lista) -> msgs.add(data+" "+lista.stream().mapToInt(vo -> vo.getFavoriteCount()).sum()+" Favorites"));
		return this;
	}
	
	public FiltraTwitter imprimeDataMaisRecente(){
		 msgs.add("====== DATA MAIS RECENTE ======");
		 msgs.add(map.keySet().stream().sorted((d1,d2) -> d2.compareTo(d1)).findFirst().get().toString());
		return this;
	}
	
	public FiltraTwitter imprimeDataMenosRecente(){
		 msgs.add("====== DATA MENOS RECENTE ======");
		 msgs.add(map.keySet().stream().sorted((d1,d2) -> d1.compareTo(d2)).findFirst().get().toString());
		return this;
	}
	
	public FiltraTwitter imprimePrimeiroUsuarioDaLista(){
		msgs.add("====== PRIMEIRO USUARIO DA LISTA ======");
		TwitterVo twitterVo = listaTodosTwitts().stream().sorted((t1,t2) -> t1.getUser().getName().compareTo(t2.getUser().getName())).findFirst().get();
		msgs.add(twitterVo.getUser().getName());
		return this;
	}
	
	public FiltraTwitter imprimeUltimoUsuarioDaLista(){
		msgs.add("====== ULTIMO USUARIO DA LISTA ======");
		TwitterVo twitterVo2 = listaTodosTwitts().stream().sorted((t1,t2) -> t2.getUser().getName().compareTo(t1.getUser().getName())).findFirst().get();
		msgs.add(twitterVo2.getUser().getName());
		return this;
	}

	private Set<TwitterVo> listaTodosTwitts(){
		Set<TwitterVo> listaTotal = new HashSet<>();
		map.forEach((data,lista) -> {listaTotal.addAll(lista);});
		return listaTotal;
	}
	
}
