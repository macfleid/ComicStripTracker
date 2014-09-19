CREATE VIEW [VIEW_COLLECTION_WATCHLIST] as 
SELECT Distinct Collection.[name] as collectionName, 
 Collection.[_id], 
 COUNT(*) as collectionCount 
 FROM Collection 
 INNER JOIN Collection_Has_BD ON Collection_Has_BD.[Collection_id]=Collection.[_id] 
 INNER JOIN BD ON BD.[_id]=Collection_Has_BD.[BD_id] 
 INNER JOIN Watchlist ON BD.[_id]=Watchlist.[BD__id]
 GROUP BY Collection.[_id], Collection_Has_BD.[Collection_id];
 
 CREATE VIEW [VIEW_COLLECTION_LIBRARY] as 
SELECT Distinct Collection.[name] as collectionName, 
 Collection.[_id], 
 COUNT(*) as collectionCount 
 FROM Collection 
 INNER JOIN Collection_Has_BD ON Collection_Has_BD.[Collection_id]=Collection.[_id] 
 INNER JOIN BD ON BD.[_id]=Collection_Has_BD.[BD_id] 
 INNER JOIN Bought_Comics ON BD.[_id]=Bought_Comics.[BD__id]
 GROUP BY Collection.[_id], Collection_Has_BD.[Collection_id];
 
  CREATE VIEW [VIEW_COLLECTION_LIBRARY_ITEMS] as 
SELECT BD.[name] as comics_name, Collection.[name] as Collection_name, Bought_Comics.[date] as boughtDate, BD.[date] as BdDate, 
 Collection_Has_BD.[order] as comic_order, BD.[_id] 
 FROM Collection 
 INNER JOIN Collection_Has_BD ON Collection_Has_BD.[Collection_id]=Collection.[_id] 
 INNER JOIN BD ON BD.[_id]=Collection_Has_BD.[BD_id] 
 INNER JOIN Bought_Comics ON BD.[_id]=Bought_Comics.[BD__id]
 GROUP BY Collection.[_id], Collection_Has_BD.[Collection_id], comics_name 
 ORDER BY Collection_Has_BD.[order];
 
   CREATE VIEW [VIEW_WATCHLIST_ITEMS] as 
SELECT  Distinct BD.[name] as comics_name, Collection.[name] as Collection_name, Watchlist.[date] as boughtDate, BD.[date] as BdDate, 
 Collection_Has_BD.[order] as comic_order, BD.[_id] 
 FROM Collection 
 INNER JOIN Collection_Has_BD ON Collection_Has_BD.[Collection_id]=Collection.[_id] 
 INNER JOIN BD ON BD.[_id]=Collection_Has_BD.[BD_id] 
 INNER JOIN Watchlist ON BD.[_id]=Watchlist.[BD__id]
 GROUP BY Collection.[_id], Collection_Has_BD.[Collection_id], comics_name 
 ORDER BY Collection_Has_BD.[order];