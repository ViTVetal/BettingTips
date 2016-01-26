class AddFieldToEvents < ActiveRecord::Migration
  def change
  	add_column :events, :odds, :float
  	add_column :events, :score1, :integer
  	add_column :events, :score2, :integer
  	add_column :events, :success, :boolean
  end
end
