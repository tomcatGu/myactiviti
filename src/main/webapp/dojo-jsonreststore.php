<?php
/*
 * Simple PHP page that checks if all HTTP request methods are supported by your apache installation
 * and directly writes the parameters back as json
 */
if (array_key_exists('PATH_INFO', $_SERVER)) {
	$arr = null;
	$resource = $_SERVER['PATH_INFO'];
	$method = $_SERVER['REQUEST_METHOD'];	

	if ($method == 'POST' || $method == 'PUT') {
		parse_str(file_get_contents('php://input'), $_DATA);
	}
	else {
		$_DATA = $_GET;
	}
	
	$arr = array(
		'method' => $method,
		'resource' => $resource,
		'data' => $_DATA
	);	

// GET = load all tree items
// in a real world example you would generate the tree items
// from a database or from the filesystem.
if ($method == 'GET') {
	switch($resource) {
		case '/':
			$arr = array( 
				array('$ref' => 'node1', 'name' => 'node1', 'children' => true),
				array('id' => 'node2', 'name' =>'node2', 'someProperty' =>'somePropertyB'),
				array('id' => 'node3', 'name' =>'node3', 'someProperty' =>'somePropertyC'),
				array('id' => 'node4', 'name' =>'node4', 'someProperty' =>'somePropertyA'),
				array('id' => 'node5', 'name' =>'node5', 'someProperty' =>'somePropertyB')
			);
			break;
		case '/node1':
			$arr = array('id' => 'node1', 'name' => 'node1', 'someProperty' => 'somePropertyA', 'children' => array(
				array('$ref' => 'node1.1', 'name' => 'node1.1', 'children' => true),
				array('name' => 'node1.2')
			));
			break;
		case '/node1.1':
			$arr = array('id' => 'node1.1', 'name' =>'node1.1', 'someProperty' => 'somePropertyA1', 'children' => array(
				array('id' => 'node1.1.3', 'name' => 'node1.1.3'),
				array('$ref' => 'node1.1.1', 'name' => 'node1.1.1'),
				array('$ref' => 'node1.1.2', 'name' => 'node1.1.2')
			));
			break;
	}
	$status = 'HTTP/1.1 200 OK';
}

// POST = create new item 6 and append it to node 5
else if ($method == 'POST') {
	$arr = array(
		array('id' => 'node6', '$ref' => 'node5', 'name' => 'node6')
	);
	$status = 'HTTP/1.1 201 Created';
}

// PUT = update item 2 (e.g. rename)
else if ($method == 'PUT') {
	// You would use $resource to decide which item to update
	$status = 'HTTP/1.1 200 OK';
	// $status = 'HTTP/1.1 404 Not Found';	// returning this would prevent renaming
}

// DELETE
else if ($method == 'DELETE') {
	// You would use $resource to decide which item to delete
	
	/* HTTP status
   A successful response SHOULD be 200 (OK) if the response includes an
   entity describing the status, 202 (Accepted) if the action has not
   yet been enacted, or 204 (No Content) if the action has been enacted
   but the response does not include an entity.
	*/
	//$status = 'HTTP/1.1 500 Internal Server Error';	// returning this would prevent deleting
	//$status = 'HTTP/1.1 204 No Content';
	$status = 'HTTP/1.1 200 OK';
}

header($status);
echo json_encode($arr);
}
else {
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN"
   "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>REST with Dojo and PHP</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
var djConfig = {
	parseOnLoad: false,
	isDebug: false,
	useCommentedJson: true
};
</script>
<script type="text/javascript" src="http://o.aolcdn.com/dojo/1.4.1/dojo/dojo.xd.js"></script>
<script type="text/javascript">
dojo.require('dojox.data.JsonRestStore');
dojo.require('dijit.Tree');

var demo = {
	store: null,
	model: null,
	tree: null,

	init: function() {
		this.store = new dojox.data.JsonRestStore({
			target: 'dojo-jsonreststore.php',
			labelAttribute: "name"
		});

		this.model = new dijit.tree.ForestStoreModel({
			store: this.store,
			deferItemLoadingUntilExpand: true,
			rootId: "images",
			rootLabel: "images",
			query: {},			
			childrenAttrs: ['children']
		});
		
		this.tree = new dijit.Tree({
			model: this.model,
			persist: false
		}, 'tree');
		this.tree.startup();
	},

	/*** CRUD operations ***
	 * Note: Updating data in the tree's JsonRestStore wille make the ForestStoreModel adapter 
	 * re-query the top nodes on every onNew notification event and every onDelete event that involves a top level item.
	 * This can result in queries to the server even though the server has not yet been sent all changes.
	 * This makes top level additions essentially disappear when the re-query takes place. You may need to 
	 * override the _onNewItem and _onDeleteItem to provide your own logic about where new and deleted items should be placed in the hierarchy.
	 */
		
	// GET = read item 1
	get: function() {
		this.store.fetchItemByIdentity({
			identity: 'node1',
			onItem: dojo.hitch(this.store, function(item) {
				var children = this.getValue(item, 'children');
				dojo.byId('log').innerHTML+= 'item ' + item.id + ' contains:<br/>';
				dojo.forEach(children, function(child) {
					dojo.byId('log').innerHTML+= 'item ' + child.name + '<br/>';
				});
			}),
			onError: function(error) {
				dojo.byId('log').innerHTML+= 'fetching item failed with ' + error + '<br/>';
			}
		});
	},
	
	// PUT = update item 2
	put: function() {
		this.store.fetchItemByIdentity({
			identity: 'node2',
			onItem: dojo.hitch(this.store, function(item) {
				this.setValue(item, 'name', 'renamed');	// updating will requery this will reload
				this.save({
					onComplete: function() {
						dojo.byId('log').innerHTML+= 'item successfully renamed<br/>';
					},
					onError: function(error) {
						dojo.byId('log').innerHTML+= 'updating item failed with ' + error + '<br/>';
					}
				});
			}),
			onError: function(error) {
				dojo.byId('log').innerHTML+= 'fetching item failed with: ' + error + '<br/>';
			}
		});
	},

	// DELETE = delete item 3
	del: function() {
		this.store.fetchItemByIdentity({
			identity: 'node3',
			onItem: dojo.hitch(this.store, function(item) {
				this.deleteItem(item);
				this.save({
					onComplete: function(item) {
						dojo.byId('log').innerHTML+= 'item successfully deleted<br/>';
					},
					onError: function(error) {
						dojo.byId('log').innerHTML+= 'deleting item failed with: ' + error + '<br/>';
						app.store.revert();	// has to be called explicitly compare to PUT/POST
					}
				});
			}),
			onError: function(error) {
				dojo.byId('log').innerHTML+= 'fetching item failed with: ' + error + '<br/>';
			}
		});
	},
	
	// POST = create new item 6 as child of node 5	
	post: function() {
		this.store.fetchItemByIdentity({
			identity: 'node5',
			onItem: dojo.hitch(this, function(item) { 				
				this.model.newItem({
					name: 'new node 6'
				}, item);
				this.store.save({
					onComplete: function() {
						dojo.byId('log').innerHTML+= 'new item successfully created<br/>';
						// update store to display folder icon
						//'dijitFolderClosed'
					},
					onError: function(error) {
						dojo.byId('log').innerHTML+= 'creating new item fails with: ' + error + '<br/>';
					}
				});
			}),
			onError: function() {
				dojo.byId('log').innerHTML+= 'fetching item failed with: ' + error + '<br/>';
			}
		});
		// didn't get this to work, e.g. append to root
		/* 
		var onItem = dojo.hitch(this, function(root) {
			console.debug(root)
			this.model.newItem({
				name: 'new node 2'
			}, root);
			this.store.save();
		});
		var onError = dojo.hitch(this, function(error) {
			console.debug('createing new item fails with: ', error);
		});
		this.model.getRoot(onItem, onError);
		*/
	}	
};

dojo.addOnLoad(function() {
	demo.init();
	// add each request method to one of the links
	dojo.query('#ulDemo li').forEach(function(node, i) {
		dojo.connect(node, 'onclick', function(e) {
			dojo.stopEvent(e);	// prevent link action
			switch(i) {
				case 0:
					demo.post();
					break;
				case 1:
					demo.get();
					break;
				case 2:
					demo.put();
					break;
				case 3:
					demo.del();	// note: delete is reserved word
					break;
			}
		});		
	});
});
</script>
<style type="text/css">
@import "/library/dojo/dijit/themes/soria/soria.css";
@import "/library/dojo/dojo/resources/dojo.css";
</style>
<style type="text/css">
body {
	padding: 20px;
}

#log {
	padding: 8px;
	border: 1px solid #ccc;
	height: 200px;
	overflow: auto;
}
</style>
</head>

<body class="soria">
<h1>Lazy load, update, create and delete dijit tree items</h1>
<p>The root of the tree (images) is loaded with a GET request on page load. When the users expands a folder, a new GET request is issued
to fetch its contents and the response is cached. Creating/deleting a tree item will revert all changes in the tree, because the JsonRestStore sends a GET first and the
server response does not contain these previous changes (in this demo they are simply not implemented. The server just sends a 200 or 500.</p>
<p>This demo belongs to part 2 of the tutorial: <a href="index.php?p=91">REST with Dojo and PHP</a>.</p>
<h2><a name="demo"></a>Demo of creating, modifying and deleting tree items lazily.</h2>
<div id="tree"></div>
<ul id="ulDemo" class="main">
<li>POST: <a href="#">create new item 6 as child of item 5</a> (TODO: fix icon, which is not updated)</li>
<li>GET: <a href="#">load data of item 1</a></li>
<li>PUT: <a href="#">rename tree item 2</a></li>
<li>DELETE: <a href="#">delete item 3</a></li>
</ul>
<div>log window</div>
<div id="log"></div>
<p>Download the zipped demo file <a href="dojo-jsonreststore.zip">dojo-jsonreststore.zip</a></p>
</body>
</html>
<?php } ?>