class EditEvents < ActiveRecord::Migration
  def change
  	remove_index :events, :category if index_exists?(:events, :category)
  	remove_column :events, :category

  	add_column :events, :category_id, :integer
  	add_index  :events, :category_id
  end
end
